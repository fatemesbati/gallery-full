package models.post;

import com.google.inject.ImplementedBy;

import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

/**
 * This interface provides a non-blocking API for possibly blocking operations.
 */
@ImplementedBy(JPAPostRepository.class)
public interface PostRepository {

    CompletionStage<Post> add(Post person);

    CompletionStage<Stream<Post>> list();
}
