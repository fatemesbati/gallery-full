package controllers;

import com.google.common.collect.Lists;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import models.comment.Comment;
import models.comment.CommentRepository;
import models.comment.JPACommentRepository;
import models.post.Post;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import play.data.FormFactory;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.concurrent.CompletionStage;


public class CommentController extends Controller {
    private final FormFactory formFactory;
    private final CommentRepository commentRepository;
    private final JPACommentRepository JPAcommentRepository;
    private final HttpExecutionContext ec;
    private final Config config = ConfigFactory.load();

    @Inject
    public CommentController(FormFactory formFactory, CommentRepository commentRepository, JPACommentRepository jpAcommentRepository, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.commentRepository = commentRepository;
        this.JPAcommentRepository = jpAcommentRepository;
        this.ec = ec;
    }

    public Result addComment(final Http.Request request) {
        Comment newComment = formFactory.form(Comment.class).bindFromRequest(request).get();
        commentRepository.add(newComment);
        return ok();
    }

    public CompletionStage<Result> list(final Http.Request request){
        Comment newComment = formFactory.form(Comment.class).bindFromRequest(request).get();
        return commentRepository
                .list(newComment.getPost())
                .thenApplyAsync(posts -> ok(Json.toJson(posts)), ec.current());
    }
}
