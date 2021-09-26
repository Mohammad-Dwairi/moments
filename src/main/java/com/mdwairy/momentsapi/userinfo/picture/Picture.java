package com.mdwairy.momentsapi.userinfo.picture;

import com.mdwairy.momentsapi.appentity.AppEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static javax.persistence.EnumType.STRING;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Picture extends AppEntity {

    @NotBlank(message = "Invalid image URL")
    private String url;

    @NotNull(message = "Invalid image type")
    @Enumerated(STRING)
    private PictureType type;
}
