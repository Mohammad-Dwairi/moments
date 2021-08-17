package com.mdwairy.momentsapi.app.userdetails;

import com.mdwairy.momentsapi.users.User;
import com.mdwairy.momentsapi.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserDetailsJPA implements AppUserDetailsService {

    private final AppUserDetailsRepository detailsRepository;
    private final UserService userService;

    @Override
    public String saveProfilePicture(String pictureUrl) {
        User user = userService.getUserFromSecurityContext();
        var userDetailsOptional = detailsRepository.findById(user.getAppUserDetails().getId());
        if (userDetailsOptional.isPresent()) {
            AppUserDetails userDetails = userDetailsOptional.get();
            userDetails.setCurrentProfilePicture(pictureUrl);
            userDetails.addProfilePicture(pictureUrl);
            detailsRepository.save(userDetails);
            return pictureUrl;
        }
        throw new RuntimeException("User details not found!");
    }

    @Override
    public void deleteCurrentProfilePicture() {
        User user = userService.getUserFromSecurityContext();
        var userDetailsOptional = detailsRepository.findById(user.getAppUserDetails().getId());

        if (userDetailsOptional.isPresent()) {
            AppUserDetails userDetails = userDetailsOptional.get();
            userDetails.setCurrentProfilePicture(null);
            detailsRepository.save(userDetails);
            return;
        }

        throw new RuntimeException("User details not found!");
    }

    @Override
    public void deleteProfilePicture(String pictureUrl) {
        User user = userService.getUserFromSecurityContext();
        System.out.println(user.getId());
        var userDetailsOptional = detailsRepository.findById(user.getAppUserDetails().getId());

        if (userDetailsOptional.isPresent()) {
            AppUserDetails userDetails = userDetailsOptional.get();
            var pictures = userDetails.getProfilePictures();
            pictures.remove(pictureUrl);
            detailsRepository.save(userDetails);
            return;
        }

        throw new RuntimeException("User details not found!");
    }

    @Override
    public String saveCoverPicture(String pictureUrl) {
        User user = userService.getUserFromSecurityContext();
        var userDetailsOptional = detailsRepository.findById(user.getAppUserDetails().getId());
        if (userDetailsOptional.isPresent()) {
            AppUserDetails userDetails = userDetailsOptional.get();
            userDetails.setCurrentCoverPicture(pictureUrl);
            userDetails.addCoverPicture(pictureUrl);
            detailsRepository.save(userDetails);
            return pictureUrl;
        }
        throw new RuntimeException("User details not found!");
    }

    @Override
    public void deleteCurrentCoverPicture() {
        User user = userService.getUserFromSecurityContext();
        var userDetailsOptional = detailsRepository.findById(user.getAppUserDetails().getId());

        if (userDetailsOptional.isPresent()) {
            AppUserDetails userDetails = userDetailsOptional.get();
            userDetails.setCurrentCoverPicture(null);
            detailsRepository.save(userDetails);
            return;
        }

        throw new RuntimeException("User details not found!");
    }

    @Override
    public void deleteCoverPicture(String pictureUrl) {
        User user = userService.getUserFromSecurityContext();
        System.out.println(user.getId());
        var userDetailsOptional = detailsRepository.findById(user.getAppUserDetails().getId());

        if (userDetailsOptional.isPresent()) {
            AppUserDetails userDetails = userDetailsOptional.get();
            var pictures = userDetails.getCoverPictures();
            pictures.remove(pictureUrl);
            detailsRepository.save(userDetails);
            return;
        }

        throw new RuntimeException("User details not found!");
    }
}
