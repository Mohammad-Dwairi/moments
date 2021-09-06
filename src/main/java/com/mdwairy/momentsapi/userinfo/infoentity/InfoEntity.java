package com.mdwairy.momentsapi.userinfo.infoentity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mdwairy.momentsapi.userinfo.UserInfo;
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
    protected UserInfo userInfo;

    @JsonProperty(access = READ_ONLY)
    protected Date createdAt = new Date();

    @JsonProperty(access = WRITE_ONLY)
    protected Boolean isVisible;
}
