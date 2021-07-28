package com.mdwairy.momentsapi.registration;

import com.mdwairy.momentsapi.exception.UserAlreadyExistsException;
import com.mdwairy.momentsapi.users.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.mail.SendFailedException;
import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path ="/registration", produces = "application/json")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public User registerNewUser(@RequestBody @Valid RegistrationRequest request) throws SendFailedException {
       return registrationService.register(request);
    }

    @GetMapping("/confirm")
    public String confirmEmail(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
