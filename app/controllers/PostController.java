package controllers;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import models.comment.CommentRepository;
import models.post.Post;
import models.post.PostRepository;
import play.data.FormFactory;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static play.libs.Json.toJson;

/**
 * The controller keeps all database operations behind the repository, and uses
 * {@link HttpExecutionContext} to provide access to the
 * {@link Http.Context} methods like {@code request()} and {@code flash()}.
 */
public class PostController extends Controller {

    private final FormFactory formFactory;
    private final PostRepository postRepository;
    private final HttpExecutionContext ec;
    private final Config config = ConfigFactory.load();

    @Inject
    public PostController(FormFactory formFactory, PostRepository postRepository, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.postRepository = postRepository;
        this.ec = ec;
    }

    public CompletionStage<Result> getPosts() {
        return postRepository
                .list()
                .thenApplyAsync(posts -> ok(Json.toJson(posts)), ec.current());
    }

    @BodyParser.Of(MyMultipartFormDataBodyParser.class)
    public CompletionStage<Result> uploadPost(Http.Request request) throws IOException {

        Http.MultipartFormData<File> body;
        Http.MultipartFormData.FilePart<File> uploadedFile;
        File file = null;
        String fileName = null;
        body = request.body().asMultipartFormData();
        uploadedFile = body.getFile("upload");
        if (uploadedFile != null) {
            file = uploadedFile.getRef();
            fileName = uploadedFile.getFilename();
        }

        String tmpPath = config.getString("image_file_tmp");
        Path tmpDir = Paths.get(tmpPath);

        if (Files.notExists(tmpDir)) {
            Files.createDirectory(tmpDir);
        }

        // todo: change this - cannot upload file with complicated name
        Files.move(Paths.get(file.getAbsolutePath()), Paths.get(tmpPath + fileName),
                StandardCopyOption.REPLACE_EXISTING);

        Post newPost = new Post(fileName);
        return postRepository
                .add(newPost)
                .thenApplyAsync(post ->
                        created(Json.toJson(post)), ec.current());
    }

    public Result servePost(String fileName) {
        return GetFileStream(config.getString("image_file_tmp") + fileName);
    }

    public static Result GetFileStream(String address) {
        File file = new File(address);
        if (file.exists())
            return ok(file);
        else
            return notFound("not found");
    }

}
