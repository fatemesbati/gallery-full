package models.post;

import models.comment.Comment;
import javax.persistence.*;
import java.util.*;


@Entity
public class Post {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;
    private String image;
    public Post() {
    }
    public Post(String image) {
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}