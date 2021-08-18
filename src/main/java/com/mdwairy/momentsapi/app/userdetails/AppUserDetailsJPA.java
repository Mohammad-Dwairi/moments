package com.mdwairy.momentsapi.app.userdetails;

import com.mdwairy.momentsapi.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AppUserDetailsJPA implements AppUserDetailsService {

    private final AppUserDetailsRepository detailsRepository;
    private final UserService userService;

    @Override
    public String saveProfilePicture(String pictureUrl) {
        AppUserDetails userDetails = userService.getUserFromSecurityContext().getAppUserDetails();
        userDetails.setCurrentProfilePicture(pictureUrl);
        userDetails.addProfilePicture(pictureUrl);
        return detailsRepository.save(userDetails).getCurrentProfilePicture();
    }

    @Override
    public void deleteCurrentProfilePicture() {
        AppUserDetails userDetails = userService.getUserFromSecurityContext().getAppUserDetails();
        userDetails.setCurrentProfilePicture(null);
        detailsRepository.save(userDetails);
    }

    @Override
    public void deleteProfilePicture(String pictureUrl) {
        AppUserDetails userDetails = userService.getUserFromSecurityContext().getAppUserDetails();
        var pictures = userDetails.getProfilePictures();
        pictures.remove(pictureUrl);
        if (Objects.equals(userDetails.getCurrentProfilePicture(), pictureUrl)) {
            userDetails.setCurrentProfilePicture(null);
        }
        detailsRepository.save(userDetails);
    }

    @Override
    public String saveCoverPicture(String pictureUrl) {
        AppUserDetails userDetails = userService.getUserFromSecurityContext().getAppUserDetails();
        userDetails.setCurrentCoverPicture(pictureUrl);
        userDetails.addCoverPicture(pictureUrl);
        return detailsRepository.save(userDetails).getCurrentCoverPicture();
    }

    @Override
    public void deleteCurrentCoverPicture() {
        AppUserDetails userDetails = userService.getUserFromSecurityContext().getAppUserDetails();
        userDetails.setCurrentCoverPicture(null);
        detailsRepository.save(userDetails);
    }

    @Override
    public void deleteCoverPicture(String pictureUrl) {
        AppUserDetails userDetails = userService.getUserFromSecurityContext().getAppUserDetails();
        var pictures = userDetails.getCoverPictures();
        pictures.remove(pictureUrl);
        if (userDetails.getCurrentCoverPicture().equals(pictureUrl)) {
            userDetails.setCurrentCoverPicture(null);
        }
        detailsRepository.save(userDetails);
    }
}
