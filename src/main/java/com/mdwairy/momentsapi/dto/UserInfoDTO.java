package com.mdwairy.momentsapi.dto;

import com.mdwairy.momentsapi.userinfo.picture.Picture;
import lombok.Data;

import java.util.Date;

@Data
public class UserInfoDTO {

    private Picture currentProfilePicture;
    private Picture currentCoverPicture;
    private String currentCity;
    private String currentCountry;
    private String nationality;
    private String bio;
    private Date joinDate;

}
