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
    public CompletionStage<Stream<Comment>> list(Post post) {
        return supplyAsync(() -> wrap(em -> list(em, post)), executionContext);
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

    private Stream<Comment> list(EntityManager em, Post post) {
        String queryString = "SELECT a FROM Comment a WHERE a.post = :post";
        TypedQuery<Comment> query =  em.createQuery(queryString, Comment.class);
        query.setParameter("post", post);
        List<Comment> comments = query.getResultList();
        return comments.stream();
    }

    private Comment delete(EntityManager em, Comment comment) {
        em.remove(em);
        return comment;
    }
}
