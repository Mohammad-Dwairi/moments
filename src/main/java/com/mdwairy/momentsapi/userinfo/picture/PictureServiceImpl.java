package com.mdwairy.momentsapi.userinfo.picture;

import com.mdwairy.momentsapi.appentity.AppEntityVisibility;
import com.mdwairy.momentsapi.exception.ResourceNotFoundException;
import com.mdwairy.momentsapi.friendship.FriendshipService;
import com.mdwairy.momentsapi.userinfo.UserInfo;
import com.mdwairy.momentsapi.users.User;
import com.mdwairy.momentsapi.users.UserSecurity;
import com.mdwairy.momentsapi.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.mdwairy.momentsapi.appentity.AppEntityVisibility.*;
import static com.mdwairy.momentsapi.constant.AppExceptionMessage.RESOURCE_NOT_FOUND;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {

    private final PictureRepository pictureRepository;
    private final UserService userService;
    private final UserSecurity userSecurity;
    private final FriendshipService friendshipService;

    @Override
    public List<Picture> findAllByUser(String username) {
        User user = userSecurity.getUserPrinciple().getUser();
        return pictureRepository.findAllByUser(user);
    }

    @Override
    public List<Picture> findAllByType(String username, PictureType type) {

        String authUsername = userSecurity.getUserPrinciple().getUsername();
        boolean isOwner = authUsername.equals(username);

        if (isOwner) {
            User user = userSecurity.getUserPrinciple().getUser();
            return pictureRepository.findAllByUserAndTypeOrderByCreatedAtDesc(user, type);
        }

        User user = userService.findByUsername(username);
        boolean isFriend = friendshipService.checkIfFriends(authUsername, username);

        if (isFriend) {
            return pictureRepository.findAllByUserAndTypeAndVisibilityIsNotOrderByCreatedAtDesc(user, type, PRIVATE);
        }

        return pictureRepository.findAllByUserAndTypeAndVisibilityOrderByCreatedAtDesc(user, type, PUBLIC);
    }

    @Override
    public Picture findById(String username, Long id) {

        String authUsername = userSecurity.getUserPrinciple().getUsername();
        if (authUsername.equals(username)) {
            return getPictureForAuthenticatedUser(id);
        }

        Picture picture = getPictureForRequestedUser(username, id);
        boolean isFriend = friendshipService.checkIfFriends(username, authUsername);
        AppEntityVisibility visibility = picture.getVisibility();

        if (isFriend && visibility == FRIENDS || visibility == PUBLIC) {
            return picture;
        }

        throw new ResourceNotFoundException(RESOURCE_NOT_FOUND);
    }

    @Override
    public Picture add(String username, Picture picture) {
        UserInfo userInfo = userService.findByUsername(username).getUserInfo();
        userInfo.addPicture(picture);
        return pictureRepository.save(picture);
    }

    @Override
    public Picture updateVisibility(Long id, AppEntityVisibility visibility) {
        Picture picture = getPictureForAuthenticatedUser(id);
        picture.setVisibility(visibility);
        return picture;
    }

    @Override
    public void deletePicture(Long id) {
        Picture picture = getPictureForAuthenticatedUser(id);
        pictureRepository.delete(picture);
    }

    private Picture getPictureForAuthenticatedUser(long id) {
        User user = userSecurity.getUserPrinciple().getUser();
        Optional<Picture> pictureOptional = pictureRepository.findPictureByUserAndId(user, id);
        return pictureOptional.orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));
    }

    private Picture getPictureForRequestedUser(String username, long id) {
        User user = userService.findByUsername(username);
        Optional<Picture> pictureOptional = pictureRepository.findPictureByUserAndId(user, id);
        return pictureOptional.orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));
    }

}