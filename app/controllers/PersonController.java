package controllers;

import models.Person;
import models.PersonRepository;
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
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static play.libs.Json.toJson;

/**
 * The controller keeps all database operations behind the repository, and uses
 * {@link HttpExecutionContext} to provide access to the
 * {@link Http.Context} methods like {@code request()} and {@code flash()}.
 */
public class PersonController extends Controller {

    private final FormFactory formFactory;
    private final PersonRepository personRepository;
    private final HttpExecutionContext ec;

    @Inject
    public PersonController(FormFactory formFactory, PersonRepository personRepository, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.personRepository = personRepository;
        this.ec = ec;
    }

    public CompletionStage<Result> addPerson(final Http.Request request) {
        Person person = formFactory.form(Person.class).bindFromRequest(request).get();
        return personRepository
                .add(person)
                .thenApplyAsync(persons ->
                        { return created(Json.toJson(persons));}, ec.current());
    }

    public CompletionStage<Result> getPersons() {
        return personRepository
                .list()
                .thenApplyAsync(personStream -> ok(toJson(personStream.collect(Collectors.toList()))), ec.current());
    }

    @BodyParser.Of(MyMultipartFormDataBodyParser.class)
    public Result upload(Http.Request request) throws IOException {
//        final Http.MultipartFormData<File> formData = request.body().asMultipartFormData();
//        final Http.MultipartFormData.FilePart<File> filePart = formData.getFile("name");
//        final File file = filePart.getRef();

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

        final long data = operateOnTempFile(file);
        return ok("file " + fileName + "size = " + data + "");
    }

    private long operateOnTempFile(File file) throws IOException {
        final long size = Files.size(file.toPath());
        Files.deleteIfExists(file.toPath());
        return size;
    }

}
