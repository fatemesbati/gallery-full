package models.post;

import com.google.inject.ImplementedBy;
import models.comment.Comment;

import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

/**
 * This interface provides a non-blocking API for possibly blocking operations.
 */
@ImplementedBy(JPAPostRepository.class)
public interface PostRepository {

    CompletionStage<Post> add(Post post);
    CompletionStage<Stream<Post>> list();
    CompletionStage<Post> delete(Long id);
}
