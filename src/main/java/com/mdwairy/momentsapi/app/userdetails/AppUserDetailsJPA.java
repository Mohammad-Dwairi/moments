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
    public String saveProfilePicture(String pictureUrl, String username) {
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

    @Override
    public String saveEducation(String education) {
        AppUserDetails userDetails = userService.getUserFromSecurityContext().getAppUserDetails();
        userDetails.addEducation(education);
        detailsRepository.save(userDetails);
        return "DONE!";
    }

    @Override
    public String editEducation(String oldEdu, String newEdu) {
        AppUserDetails userDetails = userService.getUserFromSecurityContext().getAppUserDetails();
        String edited = userDetails.editEducation(oldEdu, newEdu);
        detailsRepository.save(userDetails);
        return edited;
    }

    @Override
    public void deleteEducation(String education) {
        AppUserDetails userDetails = userService.getUserFromSecurityContext().getAppUserDetails();
        userDetails.deleteEducation(education);
        detailsRepository.save(userDetails);
    }

    @Override
    public void saveWork(String work) {
        AppUserDetails userDetails = userService.getUserFromSecurityContext().getAppUserDetails();
        userDetails.addWork(work);
        detailsRepository.save(userDetails);
    }

    @Override
    public void editWork(String oldWork, String newWork) {
        AppUserDetails userDetails = userService.getUserFromSecurityContext().getAppUserDetails();
        userDetails.editWork(oldWork, newWork);
        detailsRepository.save(userDetails);
    }

    @Override
    public void deleteWork(String work) {
        AppUserDetails userDetails = userService.getUserFromSecurityContext().getAppUserDetails();
        userDetails.deleteWork(work);
        detailsRepository.save(userDetails);
    }

    @Override
    public void saveLivingPlace(String livesIn) {
        AppUserDetails userDetails = userService.getUserFromSecurityContext().getAppUserDetails();
        userDetails.setLivesIn(livesIn);
        detailsRepository.save(userDetails);
    }

    @Override
    public void deleteLivingPlace() {
        AppUserDetails userDetails = userService.getUserFromSecurityContext().getAppUserDetails();
        userDetails.setLivesIn(null);
        detailsRepository.save(userDetails);
    }

    @Override
    public void saveCountry(String country) {
        AppUserDetails userDetails = userService.getUserFromSecurityContext().getAppUserDetails();
        userDetails.setCountry(country);
        detailsRepository.save(userDetails);
    }

    @Override
    public void deleteCountry() {
        AppUserDetails userDetails = userService.getUserFromSecurityContext().getAppUserDetails();
        userDetails.setCountry(null);
        detailsRepository.save(userDetails);
    }

    @Override
    public void saveBio(String bio) {
        AppUserDetails userDetails = userService.getUserFromSecurityContext().getAppUserDetails();
        userDetails.setBio(bio);
        detailsRepository.save(userDetails);
    }

    @Override
    public void deleteBio() {
        AppUserDetails userDetails = userService.getUserFromSecurityContext().getAppUserDetails();
        userDetails.setBio(null);
        detailsRepository.save(userDetails);
    }

}
