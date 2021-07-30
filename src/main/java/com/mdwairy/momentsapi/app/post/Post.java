package com.mdwairy.momentsapi.app.post;


import com.mdwairy.momentsapi.app.model.BaseEntity;
import com.mdwairy.momentsapi.users.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
public class Post extends BaseEntity {

    @Lob
    private String text;
    private LocalDateTime postedAt;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

}
