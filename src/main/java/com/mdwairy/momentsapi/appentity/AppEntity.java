package com.mdwairy.momentsapi.appentity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mdwairy.momentsapi.users.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static javax.persistence.EnumType.STRING;

@Data
@MappedSuperclass
public class AppEntity {
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
    protected AppEntityVisibility visibility;

    public AppEntity() {
        this.createdAt = new Date();
        this.visibility = AppEntityVisibility.PUBLIC;
    }
}
