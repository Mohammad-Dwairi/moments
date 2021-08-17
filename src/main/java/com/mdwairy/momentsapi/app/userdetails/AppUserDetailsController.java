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

}
