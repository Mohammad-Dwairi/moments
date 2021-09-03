package com.mdwairy.momentsapi.users;

import com.mdwairy.momentsapi.dto.UserDto;
import com.mdwairy.momentsapi.exception.InvalidJsonKeyException;
import com.mdwairy.momentsapi.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.mdwairy.momentsapi.constant.SecurityExceptionMessage.INVALID_JSON_KEY;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users", produces = APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAll().stream().map(UserMapper.INSTANCE::userToUserDto).collect(Collectors.toList());
    }

    @GetMapping("/{username}")
    public UserDto getUserByUsername(@PathVariable("username") String username) {
        User user = userService.findByUsername(username);
        return UserMapper.INSTANCE.userToUserDto(user);
    }

    @PatchMapping("/{username}/disable")
    public void disableUser(@PathVariable String username) {
        userService.disableUser(username);
    }

    @PatchMapping("/{username}/firstName")
    public void updateFirstName(@PathVariable String username, @RequestBody Map<String, String> request) {
        String FIRSTNAME = "firstName";
        if (request.containsKey(FIRSTNAME)) {
            userService.updateFirstName(username, request.get(FIRSTNAME));
        }
        else {
            throw new InvalidJsonKeyException(INVALID_JSON_KEY);
        }
    }

    @PatchMapping("/{username}/lastName")
    public void updateLastName(@PathVariable String username, @RequestBody Map<String, String> request) {
        String LASTNAME = "lastName";
        if (request.containsKey(LASTNAME)) {
            userService.updateLastName(username, request.get(LASTNAME));
        }
        else {
            throw new InvalidJsonKeyException(INVALID_JSON_KEY);
        }
    }

    @PatchMapping("/{username}/username")
    public void updateUserName(HttpServletRequest servletRequest, @PathVariable String username, @RequestBody Map<String, String> request) {
        String USERNAME = "username";
        String authorizationHeader = servletRequest.getHeader(AUTHORIZATION);
        if (request.containsKey(USERNAME)) {
            userService.updateUsername(username, request.get(USERNAME), authorizationHeader);
        }
        else {
            throw new InvalidJsonKeyException(INVALID_JSON_KEY);
        }
    }

    @PatchMapping("/{username}/password")
    public void updatePassword(@PathVariable String username, @RequestBody Map<String, String> request) {
        String OLD_PASSWORD = "oldPassword";
        String NEW_PASSWORD = "newPassword";
        if (request.containsKey(NEW_PASSWORD) && request.containsKey(OLD_PASSWORD)) {
            userService.updatePassword(username, request.get(OLD_PASSWORD), request.get(NEW_PASSWORD));
        }
        else {
            throw new InvalidJsonKeyException(INVALID_JSON_KEY);
        }
    }

    @PatchMapping("/{username}/role")
    public void updateRole(@PathVariable String username, @RequestBody Map<String, String> request) {
        String ROLE = "role";
        if (request.containsKey(ROLE)) {
            userService.updateUserRole(username, request.get(ROLE));
        }
        else {
            throw new InvalidJsonKeyException(INVALID_JSON_KEY);
        }
    }

    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable("username") String username) {
        userService.deleteByUsername(username);
    }

}
