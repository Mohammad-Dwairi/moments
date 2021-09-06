package com.mdwairy.momentsapi.userinfo;

import com.mdwairy.momentsapi.app.model.BaseEntity;
import com.mdwairy.momentsapi.userinfo.education.Education;
import com.mdwairy.momentsapi.userinfo.picture.Picture;
import com.mdwairy.momentsapi.userinfo.work.Work;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo extends BaseEntity {

    @OneToMany
    @JoinColumn(name = "user_info_id")
    private List<Picture> profilePictures;

    @OneToMany
    @JoinColumn(name = "user_info_id")
    private List<Picture> coverPictures;

    @OneToMany
    @JoinColumn(name = "user_info_id")
    private List<Education> educationList;

    @OneToMany
    @JoinColumn(name = "user_info_id")
    private List<Work> workList;

    @OneToOne
    private Picture currentProfilePicture;

    @OneToOne
    private Picture currentCoverPicture;

    private String currentCity;
    private String currentCountry;
    private String nationality;

    private String bio;

    private Date joinDate;

    public void addWork(Work work) {
        if (this.workList == null) {
            this.workList = new ArrayList<>();
        }
        this.workList.add(work);
        work.setUserInfo(this);
    }

    public void addEducation(Education education) {
        if (this.educationList == null) {
            this.educationList = new ArrayList<>();
        }
        this.educationList.add(education);
        education.setUserInfo(this);
    }

}
