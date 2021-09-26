package com.mdwairy.momentsapi.userinfo.education;

import com.mdwairy.momentsapi.appentity.AppEntityVisibility;
import com.mdwairy.momentsapi.exception.InvalidJsonKeyException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.mdwairy.momentsapi.constant.SecurityExceptionMessage.INVALID_JSON_KEY;

@RestController
@RequestMapping("/{username}/education")
@RequiredArgsConstructor
public class EducationController {

    private final EducationService educationService;

    @GetMapping
    public List<Education> findAll(@PathVariable String username) {
        return educationService.findAll(username);
    }

    @GetMapping("{id}")
    public Education findById(@PathVariable Long id, @PathVariable String username) {
        return educationService.findById(id, username);
    }

    @PostMapping
    public Education add(@RequestBody Education education) {
        return educationService.add(education);
    }

    @DeleteMapping("/{id}")
    public void deleteEducation(@PathVariable Long id) {
        educationService.deleteById(id);
    }

    @PatchMapping("/{id}/school_name")
    public Education updateSchoolName(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String KEY = "schoolName";
        if (body.containsKey(KEY)) {
            return educationService.updateSchoolName(id, body.get(KEY));
        }
        throw new InvalidJsonKeyException(INVALID_JSON_KEY);
    }

    @PatchMapping("/{id}/department")
    public Education updateDepartment(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String KEY = "department";
        if (body.containsKey(KEY)) {
            return educationService.updateDepartmentName(id, body.get(KEY));
        }
        throw new InvalidJsonKeyException(INVALID_JSON_KEY);
    }

    @PatchMapping("/{id}/current")
    public Education updateCurrentEducation(@PathVariable Long id, @RequestBody Map<String, Boolean> body) {
        String KEY = "isCurrent";
        if (body.containsKey(KEY)) {
            return educationService.updateCurrentEducation(id, body.get(KEY));
        }
        throw new InvalidJsonKeyException(INVALID_JSON_KEY);
    }

    @PatchMapping("/{id}/type")
    public Education updateEducationType(@PathVariable Long id, @RequestBody Map<String, EducationType> body) {
        String KEY = "type";
        if (body.containsKey(KEY)) {
            return educationService.updateEducationType(id, body.get(KEY));
        }
        throw new InvalidJsonKeyException(INVALID_JSON_KEY);
    }

    @PatchMapping("/{id}/visibility")
    public Education updateEducationVisibility(@PathVariable Long id, @RequestBody Map<String, AppEntityVisibility> body) {
        String KEY = "visibility";
        if (body.containsKey(KEY)) {
            return educationService.updateVisibility(id, body.get(KEY));
        }
        throw new InvalidJsonKeyException(INVALID_JSON_KEY);
    }
}
