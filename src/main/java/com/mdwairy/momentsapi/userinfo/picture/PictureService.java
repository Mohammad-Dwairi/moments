package com.mdwairy.momentsapi.userinfo.picture;

import com.mdwairy.momentsapi.userinfo.infoentity.InfoEntityVisibility;

import java.util.List;

public interface PictureService {
    List<Picture> findAllByUsername(String username);
    List<Picture> findAllByType(String username, PictureType type);
    Picture findById(String username, Long id);
    Picture add(String username, Picture picture);
    Picture updateVisibility(Long id, InfoEntityVisibility isVisible);
    void deletePicture(Long id);

}