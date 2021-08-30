package com.mdwairy.momentsapi.users;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users", produces = APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable("username") String username) {
        return userService.findByEmail(username);
    }

    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable("username") String username) {
        //TODO ADMIN or OWNER access ONlY.
        userService.deleteByUsername(username);
    }


}
