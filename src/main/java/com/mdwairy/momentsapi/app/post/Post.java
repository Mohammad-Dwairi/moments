package com.mdwairy.momentsapi.app.post;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mdwairy.momentsapi.app.comment.Comment;
import com.mdwairy.momentsapi.app.model.BaseEntity;
import com.mdwairy.momentsapi.users.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
public class Post extends BaseEntity {

    @Lob
    private String text;
    private LocalDateTime postedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "post")
    private List<Comment> comments = new LinkedList<>();

    public void addComment(Comment comment) {
        comment.setPost(this);
        this.comments.add(comment);
    }
}
