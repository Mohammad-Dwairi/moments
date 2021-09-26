package com.mdwairy.momentsapi.userinfo.education;

import com.mdwairy.momentsapi.appentity.AppEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Enumerated;

import static javax.persistence.EnumType.STRING;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Education extends AppEntity {

    @Enumerated(STRING)
    private EducationType type;

    private String schoolName;
    private String department;
    private Boolean isCurrent;

}
