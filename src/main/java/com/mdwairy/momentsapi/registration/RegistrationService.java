package com.mdwairy.momentsapi.registration;

import com.mdwairy.momentsapi.exception.UserAlreadyExistsException;
import com.mdwairy.momentsapi.mapper.RegistrationRequestMapper;
import com.mdwairy.momentsapi.users.User;
import com.mdwairy.momentsapi.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

import static com.mdwairy.momentsapi.constant.UserExceptionMessage.USER_EMAIL_ALREADY_REGISTERED;
import static com.mdwairy.momentsapi.constant.UserExceptionMessage.USER_USERNAME_ALREADY_REGISTERED;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final PasswordEncoder encoder;
    private final UserService userService;

    public void register(@Valid RegistrationRequest request) throws UserAlreadyExistsException {

        String username = request.getUsername();
        String email = request.getEmail();

        if (isUsernameExists(username)) {
            throw new UserAlreadyExistsException(String.format(USER_USERNAME_ALREADY_REGISTERED, username));
        }
        if (isEmailExists(email)) {
            throw new UserAlreadyExistsException(String.format(USER_EMAIL_ALREADY_REGISTERED, email));
        }

        User user = mapRegistrationRequestToUser(request);
        user.setPassword (encodePassword(user.getPassword()));
        userService.register(user);
    }

    private boolean isUsernameExists(String username) {
        try {
            userService.findByUsername(username);
            return true;
        } catch (UsernameNotFoundException e) {
            return false;
        }
    }

    private boolean isEmailExists(String email) {
        try {
            userService.findByEmail(email);
            return true;
        } catch (UsernameNotFoundException e) {
            return false;
        }
    }

    private User mapRegistrationRequestToUser(@Valid RegistrationRequest request) {
        return RegistrationRequestMapper.INSTANCE.registrationRequestToUser(request);
    }

    private String encodePassword(String password) {
        return encoder.encode(password);
    }

}
