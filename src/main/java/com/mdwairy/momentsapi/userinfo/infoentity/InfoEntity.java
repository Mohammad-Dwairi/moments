package com.mdwairy.momentsapi.userinfo.infoentity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mdwairy.momentsapi.users.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

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

    @JsonProperty(access = WRITE_ONLY)
    protected Boolean isVisible;

    public InfoEntity() {
        this.createdAt = new Date();
        this.isVisible = true;
    }
}
