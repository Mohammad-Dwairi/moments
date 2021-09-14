package com.mdwairy.momentsapi.userinfo.infoentity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mdwairy.momentsapi.users.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static com.mdwairy.momentsapi.userinfo.infoentity.InfoEntityVisibility.PUBLIC;
import static javax.persistence.EnumType.STRING;

@Data
@MappedSuperclass
public abstract class InfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    protected Long id;

    @OneToOne
    @JsonIgnore
    protected User user;

    @JsonProperty(access = READ_ONLY)
    protected Date createdAt;

    @Enumerated(STRING)
    protected InfoEntityVisibility visibility;

    public InfoEntity() {
        this.createdAt = new Date();
        this.visibility = PUBLIC;
    }
}
