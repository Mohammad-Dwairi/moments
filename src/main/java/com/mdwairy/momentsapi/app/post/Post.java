package com.mdwairy.momentsapi.app.post;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mdwairy.momentsapi.app.model.BaseEntity;
import com.mdwairy.momentsapi.users.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

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

}
