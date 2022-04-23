package models.post;

import models.DatabaseExecutionContext;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;

/**
 * Provide JPA operations running inside of a thread pool sized to the connection pool
 */
public class JPAPostRepository implements PostRepository {

    private final JPAApi jpaApi;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public JPAPostRepository(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
        this.jpaApi = jpaApi;
        this.executionContext = executionContext;
    }

    @Override
    public CompletionStage<Post> add(Post person) {
        return supplyAsync(() -> wrap(em -> insert(em, person)), executionContext);
    }

    @Override
    public CompletionStage<Stream<Post>> list() {
        return supplyAsync(() -> wrap(em -> list(em)), executionContext);
    }

    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Post insert(EntityManager em, Post person) {
        em.persist(person);
        return person;
    }

    private Stream<Post> list(EntityManager em) {
        List<Post> persons = em.createQuery("select p from Post p", Post.class).getResultList();
        return persons.stream();
    }
}
