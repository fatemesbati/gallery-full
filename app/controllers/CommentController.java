package controllers;

import models.comment.Comment;
import models.comment.CommentRepository;
import play.data.FormFactory;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;


public class CommentController extends Controller {
    private final FormFactory formFactory;
    private final CommentRepository commentRepository;
    private final HttpExecutionContext ec;

    @Inject
    public CommentController(FormFactory formFactory, CommentRepository commentRepository, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.commentRepository = commentRepository;
        this.ec = ec;
    }

    public Result addComment(final Http.Request request) {
        Comment newComment = formFactory.form(Comment.class).bindFromRequest(request).get();
        commentRepository.add(newComment);
        return ok();
    }

    public CompletionStage<Result> list(String id){
        // post id
        return commentRepository
                .list(Long.parseLong(id))
                .thenApplyAsync(posts -> ok(Json.toJson(posts)), ec.current());
    }

    public Result delete(String id) {
        // comment id
        commentRepository.delete(Long.parseLong(id));
        return ok();
    }
}
