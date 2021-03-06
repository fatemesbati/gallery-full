package models.comment;

import com.google.common.collect.Lists;
import models.DatabaseExecutionContext;
import models.post.Post;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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
    public CompletionStage<Stream<Comment>> list(Long id) {
        return supplyAsync(() -> wrap(em -> list(em, id)), executionContext);
    }

    @Override
    public CompletionStage<Comment> delete(Long id) {
        return supplyAsync(() -> wrap(em -> delete(em, id)), executionContext);
    }


    private <T> T wrap(Function<EntityManager, T> function) {
        return jpaApi.withTransaction(function);
    }

    private Comment insert(EntityManager em, Comment comment) {
        em.persist(comment);
        return comment;
    }

    private Stream<Comment> list(EntityManager em, Long id) {
        String queryString = "SELECT a FROM Comment a WHERE a.post.id = :id";
        TypedQuery<Comment> query =  em.createQuery(queryString, Comment.class);
        query.setParameter("id", id);
        List<Comment> comments = query.getResultList();
        return comments.stream();
    }

    private Comment delete(EntityManager em, Long id) {
        em.remove(em.find(Comment.class, id));
        return null;
    }
}
