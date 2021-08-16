package com.mdwairy.momentsapi.app.userdetails;

import com.mdwairy.momentsapi.app.model.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDetails extends BaseEntity {

    @ElementCollection
    private List<String> profilePictures;

    @ElementCollection
    private List<String> coverPictures;

    @ElementCollection
    private List<String> educations;

    @ElementCollection
    private List<String> works;

    private String currentProfilePicture;
    private String currentCoverPicture;
    private String livesIn;
    private String country;
    private String bio;
    private String joinedIn;


    public void addProfilePicture(String pictureUrl) {
        if (this.profilePictures == null) {
            this.profilePictures = new ArrayList<>();
        }
        this.profilePictures.add(pictureUrl);
    }

}
