package com.mdwairy.momentsapi.userinfo.picture;

import com.mdwairy.momentsapi.userinfo.infoentity.InfoEntityVisibility;
import com.mdwairy.momentsapi.users.User;
import org.springframework.data.repository.CrudRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface PictureRepository extends CrudRepository<Picture, Long> {

    List<Picture> findAllByUser(User user);
    List<Picture> findAllByUserAndTypeOrderByCreatedAtDesc(User user, @NotNull(message = "Invalid image type") PictureType type);
    List<Picture> findAllByUserAndTypeAndVisibilityOrderByCreatedAtDesc(User user, @NotNull(message = "Invalid image type") PictureType type, InfoEntityVisibility visibility);
    List<Picture> findAllByUserAndTypeAndVisibilityIsNotOrderByCreatedAtDesc(User user, @NotNull(message = "Invalid image type") PictureType type, InfoEntityVisibility visibility);
    Optional<Picture> findPictureByUserAndId(User user, Long id);
}
