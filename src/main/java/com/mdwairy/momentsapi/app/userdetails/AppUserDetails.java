package com.mdwairy.momentsapi.app.userdetails;

import com.mdwairy.momentsapi.app.model.BaseEntity;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.List;

@Entity
public class AppUserDetails extends BaseEntity {

    @ElementCollection
    private List<String> profilePictures;

    @ElementCollection
    private List<String> coverPictures;

    @ElementCollection
    private List<String> educations;

    @ElementCollection
    private List<String> works;

    private String livesIn;
    private String country;
    private String bio;
    private String joinedIn;

}
