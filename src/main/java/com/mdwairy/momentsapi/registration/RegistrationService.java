package com.mdwairy.momentsapi.registration;

import com.mdwairy.momentsapi.exception.UserAlreadyExistsException;
import com.mdwairy.momentsapi.users.User;
import com.mdwairy.momentsapi.users.UserRole;
import com.mdwairy.momentsapi.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
            throw new UserAlreadyExistsException();
        }
        User user = mapRegistrationRequestToUser(request);
        userService.register(user);
    }

    private boolean isUserExists(String email) {
        return userService.getUserByEmail(email) != null;
    }

    private User mapRegistrationRequestToUser(@Valid RegistrationRequest request) {
        final String HASHED_PASSWORD = encoder.encode(request.getPassword());
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(HASHED_PASSWORD);
        user.setUserRole(UserRole.USER);
        user.setLocked(false);
        user.setEnabled(true);
        return user;
    }

}
