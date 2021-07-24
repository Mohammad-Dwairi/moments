package com.mdwairy.momentsapi.registration;

import com.mdwairy.momentsapi.users.User;
import com.mdwairy.momentsapi.users.UserRole;
import com.mdwairy.momentsapi.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final BCryptPasswordEncoder encoder;
    private final UserService userService;

    public User register(@Valid RegistrationRequest request) {
        if (isUserExists(request.getEmail())) {
            throw new IllegalStateException("A user with this email is already registered");
        }

        User user = mapRegistrationRequestToUser(request);
        return userService.register(user);
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
        user.setEnabled(false);
        return user;
    }
}
