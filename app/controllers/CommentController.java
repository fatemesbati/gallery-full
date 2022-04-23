package controllers;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import models.comment.Comment;
import models.comment.CommentRepository;
import play.data.FormFactory;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import play.libs.Json;
import static play.libs.Json.toJson;


public class CommentController extends Controller {
    private final FormFactory formFactory;
    private final CommentRepository commentRepository;
    private final HttpExecutionContext ec;
    private final Config config = ConfigFactory.load();

    @Inject
    public CommentController(FormFactory formFactory, CommentRepository commentRepository, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.commentRepository = commentRepository;
        this.ec = ec;
    }

    public CompletionStage<Result> addComment(final Http.Request request) {
        Comment comment = formFactory.form(Comment.class).bindFromRequest(request).get();
        return commentRepository
                .add(comment)
                .thenApplyAsync(persons ->
                        created(Json.toJson(persons)), ec.current());
    }

    public CompletionStage<Result> getComments() {
        return commentRepository
                .list()
                .thenApplyAsync(personStream -> ok(toJson(personStream.collect(Collectors.toList()))), ec.current());
    }
}
