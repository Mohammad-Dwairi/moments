package com.mdwairy.momentsapi.userinfo.work;

import com.mdwairy.momentsapi.userinfo.infoentity.InfoEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@Getter
@Setter
public class Work extends InfoEntity {

    private String employerName;
    private String role;
    private Boolean isCurrent;
    private Date startingDate;
    private Date quitDate;

}
