package com.mdwairy.momentsapi.registration;

import com.mdwairy.momentsapi.users.User;
import com.mdwairy.momentsapi.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path ="/registration", produces = "application/json")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerNewUser(@RequestBody @Valid RegistrationRequest request) {
        return registrationService.register(request);
    }

    @GetMapping("/confirm")
    public String confirmEmail(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
