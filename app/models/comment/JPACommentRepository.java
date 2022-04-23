package models.comment;

import models.DatabaseExecutionContext;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class JPACommentRepository implements CommentRepository {

    private final JPAApi jpaApi;
    private final DatabaseExecutionContext executionContext;

    @Inject
    public JPACommentRepository(JPAApi jpaApi, DatabaseExecutionContext executionContext) {
        this.jpaApi = jpaApi;
        this.executionContext = executionContext;
    }

    @Override
    public CompletionStage<Comment> add(Comment comment) {
        return supplyAsync(() -> wrap(em -> insert(em, comment)), executionContext);
    }

    @Override
    public CompletionStage<Stream<Comment>> list() {
        return supplyAsync(() -> wrap(em -> list(em)), executionContext);
    }

    @Override
    public CompletionStage delete(Comment comment) {
        return supplyAsync(() -> wrap(em -> delete(em, comment)), executionContext);
    }


    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Comment insert(EntityManager em, Comment comment) {
        em.persist(comment);
        return comment;
    }

    private Stream<Comment> list(EntityManager em) {
        List<Comment> comments = em.createQuery("select p from Comment p", Comment.class).getResultList();
        return comments.stream();
    }

    private Comment delete(EntityManager em, Comment comment) {
        em.remove(em);
        return comment;
    }
}
