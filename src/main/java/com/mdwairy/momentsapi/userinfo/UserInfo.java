package com.mdwairy.momentsapi.userinfo;

import com.mdwairy.momentsapi.appentity.AppEntityVisibility;
import com.mdwairy.momentsapi.userinfo.education.Education;
import com.mdwairy.momentsapi.userinfo.picture.Picture;
import com.mdwairy.momentsapi.userinfo.work.Work;
import com.mdwairy.momentsapi.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mdwairy.momentsapi.appentity.AppEntityVisibility.PUBLIC;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    @Id
    @Column(name = "user_id")
    private Long id;

    @MapsId
    @OneToOne(mappedBy = "userInfo", fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "user", cascade = ALL)
    private List<Picture> pictures;

    @OneToMany(mappedBy = "user", cascade = ALL)
    private List<Education> educationList;

    @OneToMany(mappedBy = "user", cascade = ALL)
    private List<Work> workList;

    @OneToOne
    private Picture currentPicture;

    @OneToOne
    private Picture currentCoverPicture;

    private String currentCity;
    private String currentCountry;
    private String nationality;

    private String bio;

    private Date joinDate;

    @Enumerated(STRING)
    private AppEntityVisibility friendsVisibility = PUBLIC;

    public UserInfo(User user) {
        this.user = user;
    }

    public void addWork(Work work) {
        if (this.workList == null) {
            this.workList = new ArrayList<>();
        }
        this.workList.add(work);
        work.setUser(this.user);
    }

    public void addEducation(Education education) {
        if (this.educationList == null) {
            this.educationList = new ArrayList<>();
        }
        this.educationList.add(education);
        education.setUser(this.user);
    }

    public void addPicture(Picture picture) {
        if (this.pictures == null) {
            this.pictures = new ArrayList<>();
        }
        this.pictures.add(picture);
        picture.setUser(this.user);
    }
}
