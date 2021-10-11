package com.mdwairy.momentsapi.userinfo.work;

import com.mdwairy.momentsapi.appentity.AppEntity;
import com.mdwairy.momentsapi.users.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@Getter
@Setter
public class Work extends AppEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String employerName;
    private String role;
    private Boolean isCurrent;
    private Date startingDate;
    private Date quitDate;

}
