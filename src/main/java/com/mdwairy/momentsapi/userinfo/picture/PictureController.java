package com.mdwairy.momentsapi.userinfo.picture;


import com.mdwairy.momentsapi.exception.InvalidJsonKeyException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.mdwairy.momentsapi.constant.SecurityExceptionMessage.INVALID_JSON_KEY;

@RestController
@RequestMapping("{username}/pictures")
@RequiredArgsConstructor
public class PictureController {

    private final PictureService pictureService;

    @GetMapping
    public List<Picture> findAll(@PathVariable String username) {
        return pictureService.findAllByUsername(username);
    }

    @GetMapping(params = {"type"})
    public List<Picture> findAllByType(@RequestParam PictureType type, @PathVariable String username) {
        return pictureService.findAllByType(username, type);
    }

    @GetMapping("{id}")
    public Picture findById(@PathVariable("id") Long id, @PathVariable String username) {
        return pictureService.findById(username, id);
    }

    @PostMapping
    public Picture addProfilePicture(@RequestBody Picture picture, @PathVariable String username) {
        return pictureService.add(username, picture);
    }

    @PatchMapping("{id}/visibility")
    public Picture changePictureVisibility(@RequestBody Map<String, Boolean> body, @PathVariable Long id) {
        String KEY = "isVisible";
        if (body.containsKey(KEY)) {
            return pictureService.updateVisibility(id, body.get(KEY));
        }
        throw new InvalidJsonKeyException(INVALID_JSON_KEY);
    }

    @DeleteMapping("/{id}")
    public void deleteProfilePicture(@PathVariable Long id) {
        pictureService.deletePicture(id);
    }
}
