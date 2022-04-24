package models.comment;

import com.google.inject.ImplementedBy;
import models.post.JPAPostRepository;
import models.post.Post;

import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

@ImplementedBy(JPACommentRepository.class)
public interface CommentRepository {

    CompletionStage<Comment> add(Comment comment);
    CompletionStage<Stream<Comment>> list(Post post);
    CompletionStage<Comment> delete(Comment comment);

}
