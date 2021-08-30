package com.mdwairy.momentsapi.registration;

import com.mdwairy.momentsapi.constant.UserExceptionMessage;
import com.mdwairy.momentsapi.exception.UserAlreadyExistsException;
import com.mdwairy.momentsapi.mapper.RegistrationRequestMapper;
import com.mdwairy.momentsapi.users.User;
import com.mdwairy.momentsapi.users.UserRole;
import com.mdwairy.momentsapi.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final PasswordEncoder encoder;
    private final UserService userService;

    public void register(@Valid RegistrationRequest request) throws UserAlreadyExistsException {
        if (isUserExists(request.getEmail())) {
            throw new UserAlreadyExistsException(UserExceptionMessage.USER_ALREADY_REGISTERED);
        }
        User user = mapRegistrationRequestToUser(request);
        user.setPassword (encodePassword(user.getPassword()));
        userService.register(user);
    }

    private boolean isUserExists(String email) {
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
