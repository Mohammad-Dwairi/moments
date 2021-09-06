package com.mdwairy.momentsapi.userinfo.picture;

import com.mdwairy.momentsapi.userinfo.infoentity.InfoEntity;
import lombok.*;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Picture extends InfoEntity {

    private PictureType type;
    private String pictureUrl;
    private String description;

}
