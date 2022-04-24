package models.comment;

import models.post.Post;
import javax.persistence.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;
    private String content;

    @ManyToOne
    private Post post;

    public Comment(String content) {
        this.content = content;
    }

    public Comment() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

//    public void addComment(Comment comment) {
//        getPost().getComments().add(comment);
//    }
}

