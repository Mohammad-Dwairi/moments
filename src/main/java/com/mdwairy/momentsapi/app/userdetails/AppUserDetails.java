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

    public void addCoverPicture(String pictureUrl) {
        if (this.coverPictures == null) {
            this.coverPictures = new ArrayList<>();
        }
        this.coverPictures.add(pictureUrl);
    }

    public void addEducation(String education) {
        if (this.educations == null) {
            this.educations = new ArrayList<>();
        }
        this.educations.add(education);
    }

    public String editEducation(String oldEdu, String newEdu) {
        int i = educations.indexOf(oldEdu);
        if (i != -1) {
            educations.set(i, newEdu);
            return educations.get(i);
        }
        throw new RuntimeException("Edu not found!");
    }

    public void deleteEducation(String education) {
        boolean isDeleted = educations.remove(education);
        if (!isDeleted) {
            throw new RuntimeException("Education not found");
        }
    }

    public void addWork(String work) {
        if (works == null) {
            works = new ArrayList<>();
        }
        works.add(work);
    }

    public void editWork(String oldWork, String newWork) {
        int i = works.indexOf(oldWork);
        if (i != -1) {
            works.set(i, newWork);
            return;
        }
        throw new RuntimeException("Work not found!");
    }

    public void deleteWork(String work) {
        boolean isDeleted = works.remove(work);
        if (!isDeleted) {
            throw new RuntimeException("Work not found");
        }
    }

}
