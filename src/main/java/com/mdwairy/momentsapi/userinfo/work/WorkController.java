package com.mdwairy.momentsapi.userinfo.work;

import com.mdwairy.momentsapi.exception.InvalidJsonKeyException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.mdwairy.momentsapi.constant.SecurityExceptionMessage.INVALID_JSON_KEY;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{username}/work")
public class WorkController {

    private final WorkService workService;

    @GetMapping
    public List<Work> findAll(@PathVariable String username) {
        return workService.findAll(username);
    }

    @GetMapping("/{id}")
    public Work findWork(@PathVariable Long id, @PathVariable String username) {
        return workService.findWork(id, username);
    }

    @PostMapping
    public Work addWork(@RequestBody Work work, @PathVariable String username) {
        return workService.addWork(work, username);
    }

    @PatchMapping("{id}/visibility")
    public Work updateVisibility(@RequestBody Map<String, Boolean> body, @PathVariable String username, @PathVariable Long id) {
        final String KEY = "isVisible";
        if (body.containsKey(KEY)) {
            return workService.updateVisibility(id, body.get(KEY));
        }
        throw new InvalidJsonKeyException(INVALID_JSON_KEY);
    }

    @PatchMapping("{id}/employer")
    public Work updateEmployerName(@RequestBody Map<String, String> body, @PathVariable Long id, @PathVariable String username) {
        final String KEY = "employer";
        if (body.containsKey(KEY)) {
            return workService.updateEmployerName(id, body.get(KEY));
        }
        throw new InvalidJsonKeyException(INVALID_JSON_KEY);
    }

    @PatchMapping("{id}/role")
    public Work updateRole(@RequestBody Map<String, String> body, @PathVariable Long id, @PathVariable String username) {
        final String KEY = "role";
        if (body.containsKey(KEY)) {
            return workService.updateRole(id, body.get(KEY));
        }
        throw new InvalidJsonKeyException(INVALID_JSON_KEY);
    }

    @PatchMapping("{id}/current")
    public Work updateCurrentWork(@RequestBody Map<String, Boolean> body, @PathVariable Long id, @PathVariable String username) {
        final String KEY = "isCurrent";
        if (body.containsKey(KEY)) {
            return workService.updateIsCurrent(id, body.get(KEY));
        }
        throw new InvalidJsonKeyException(INVALID_JSON_KEY);
    }

    @PatchMapping("{id}/starting_date")
    public Work updateStartingDate(@RequestBody Map<String, Date> body, @PathVariable Long id, @PathVariable String username) {
        final String KEY = "startingDate";
        if (body.containsKey(KEY)) {
            return workService.updateStartingDate(id, body.get(KEY));
        }
        throw new InvalidJsonKeyException(INVALID_JSON_KEY);
    }

    @PatchMapping("{id}/quit_date")
    public Work updateQuitDate(@RequestBody Map<String, Date> body, @PathVariable Long id, @PathVariable String username) {
        final String KEY = "quitDate";
        if (body.containsKey(KEY)) {
            return workService.updateQuitDate(id, body.get(KEY));
        }
        throw new InvalidJsonKeyException(INVALID_JSON_KEY);
    }

    @DeleteMapping("{id}")
    public void deleteWork(@PathVariable String username, @PathVariable Long id) {
        workService.deleteWork(id, username);
    }

}
