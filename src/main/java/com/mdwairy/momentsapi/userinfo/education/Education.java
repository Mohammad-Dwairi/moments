package com.mdwairy.momentsapi.userinfo.education;

import com.mdwairy.momentsapi.appentity.AppEntity;
import com.mdwairy.momentsapi.users.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.EnumType.STRING;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Education extends AppEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(STRING)
    private EducationType type;

    private String schoolName;
    private String department;
    private Boolean isCurrent;

}
