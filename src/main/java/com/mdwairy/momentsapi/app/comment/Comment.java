package com.mdwairy.momentsapi.app.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mdwairy.momentsapi.app.model.BaseEntity;
import com.mdwairy.momentsapi.app.post.Post;
import com.mdwairy.momentsapi.users.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Comment extends BaseEntity {

    private String text;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    @JsonIgnore
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}
