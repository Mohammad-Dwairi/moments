package com.mdwairy.momentsapi.app.userdetails;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/details", consumes = APPLICATION_JSON_VALUE)
public class AppUserDetailsController {

    private final AppUserDetailsService detailsService;

    @PostMapping("/profile_pictures")
    public String addNewProfilePicture(@RequestBody Map<String, String> requestBody) {
        if (requestBody.containsKey("pictureUrl")) {
            return detailsService.saveProfilePicture(requestBody.get("pictureUrl"));
        }
        throw new RuntimeException("Something went wrong!");
    }

    @DeleteMapping("/profile_pictures/current")
    public void removeCurrentProfilePicture() {
        detailsService.deleteCurrentProfilePicture();
    }

    @DeleteMapping("/profile_pictures/{pictureUrl}")
    public void removeProfilePicture(@PathVariable String pictureUrl) {
        detailsService.deleteProfilePicture(pictureUrl);
    }

    @PostMapping("/cover_pictures")
    public String addNewCoverPicture(@RequestBody Map<String, String> requestBody) {
        if (requestBody.containsKey("pictureUrl")) {
            return detailsService.saveCoverPicture(requestBody.get("pictureUrl"));
        }
        throw new RuntimeException("Something went wrong!");
    }

    @DeleteMapping("/cover_pictures/current")
    public void removeCurrentCoverPicture() {
        detailsService.deleteCurrentCoverPicture();
    }

    @DeleteMapping("/cover_pictures/{pictureUrl}")
    public void removeCoverPicture(@PathVariable String pictureUrl) {
        detailsService.deleteCoverPicture(pictureUrl);
    }

    @PostMapping("/education")
    public String addNewEducation(@RequestBody Map<String, String> requestBody) {
        if (requestBody.containsKey("education")) {
            return detailsService.saveEducation(requestBody.get("education"));
        }
        throw new RuntimeException("Something went wrong!");
    }

    @PutMapping("/education")
    public String editExistingEducation(@RequestBody Map<String, String> requestBody) {
        if (requestBody.containsKey("old") && requestBody.containsKey("new")) {
            return detailsService.editEducation(requestBody.get("old"), requestBody.get("new"));
        }
        throw new RuntimeException("Something went wrong!");
    }

    @DeleteMapping("/education")
    public void deleteEducation(@RequestBody Map<String, String> requestBody) {
        if (requestBody.containsKey("education")) {
            detailsService.deleteEducation(requestBody.get("education"));
            return;
        }
        throw new RuntimeException("Something went wrong!");
    }

    @PostMapping("/work")
    public void addNewWork(@RequestBody Map<String, String> requestBody) {
        if (requestBody.containsKey("work")) {
            detailsService.saveWork(requestBody.get("work"));
            return;
        }
        throw new RuntimeException("Something went wrong!");
    }

    @PutMapping("/work")
    public void editWork(@RequestBody Map<String, String> requestBody) {
        if (requestBody.containsKey("old") && requestBody.containsKey("new")) {
            detailsService.editWork(requestBody.get("old"), requestBody.get("new"));
        }
    }

    @DeleteMapping("/work")
    public void deleteWork(@RequestBody Map<String, String> requestBody) {
        if (requestBody.containsKey("work")) {
            detailsService.deleteWork(requestBody.get("work"));
        }
    }

    @PostMapping("/living_place")
    public void setLivingPlace(@RequestBody Map<String, String> requestBody) {
        if (requestBody.containsKey("livesIn")) {
            detailsService.saveLivingPlace(requestBody.get("livesIn"));
        }
    }

    @DeleteMapping("/living_place")
    public void deleteLivingPlace() {
        detailsService.deleteLivingPlace();
    }

    @PostMapping("/country")
    public void setCountry(@RequestBody Map<String, String> requestBody) {
        if (requestBody.containsKey("country")) {
            detailsService.saveCountry(requestBody.get("country"));
        }
    }

    @DeleteMapping("/country")
    public void deleteCountry() {
        detailsService.deleteCountry();
    }

    @PostMapping("/bio")
    public void setBio(@RequestBody Map<String, String> requestBody) {
        if (requestBody.containsKey("bio")) {
            detailsService.saveBio(requestBody.get("bio"));
        }
    }

    @DeleteMapping("/bio")
    public void deleteBio() {
        detailsService.deleteBio();
    }

}
