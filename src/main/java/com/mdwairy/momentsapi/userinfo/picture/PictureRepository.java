package com.mdwairy.momentsapi.userinfo.picture;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PictureRepository extends CrudRepository<Picture, Long> {

    @Query(" SELECT picture FROM Picture picture " +
            "WHERE picture.user.username = ?1 " +
            "AND picture.type = ?2 " +
            "ORDER BY picture.createdAt DESC")
    List<Picture> findAllByType(String username, PictureType type);

    @Query("SELECT p FROM Picture p WHERE (p.user.username = ?1) AND (p.isVisible = true or p.user.username = ?2) ORDER BY p.createdAt DESC")
    List<Picture> findAllByUsername(String username, String authName);

}
