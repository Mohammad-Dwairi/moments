package com.mdwairy.momentsapi.registration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdwairy.momentsapi.users.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

import static org.springframework.http.HttpStatus.CONFLICT;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path ="/registration", produces = "application/json")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping(consumes = "application/json")
    //@ResponseStatus(HttpStatus.CREATED)
    public User registerNewUser(@RequestBody @Valid RegistrationRequest request, HttpServletResponse response) throws IOException {
        try {
            return registrationService.register(request);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            response.setStatus(CONFLICT.value());
            new ObjectMapper().writeValue(response.getOutputStream(), e.getMessage());
            return null;
        }
    }

    @GetMapping("/confirm")
    public String confirmEmail(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
