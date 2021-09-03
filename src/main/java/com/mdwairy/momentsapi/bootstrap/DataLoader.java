package com.mdwairy.momentsapi.bootstrap;

import com.mdwairy.momentsapi.app.userdetails.AppUserDetails;
import com.mdwairy.momentsapi.registration.RegistrationRequest;
import com.mdwairy.momentsapi.registration.RegistrationService;
import com.mdwairy.momentsapi.users.User;
import com.mdwairy.momentsapi.users.UserRole;
import com.mdwairy.momentsapi.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.mdwairy.momentsapi.users.UserRole.ROLE_ADMIN;

@Slf4j
@Profile("dev")
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) {
        User user = new User();
        user.setFirstName("Mohammad");
        user.setLastName("Dwairi");
        user.setEmail("mdwairy@gmail.com");
        user.setUsername("mohammad_dwairi");
        user.setPassword(passwordEncoder.encode("12345"));
        user.setRole(ROLE_ADMIN);
        user.setIsAccountLocked(false);
        user.setIsAccountEnabled(true);

        userService.register(user);
        log.info("User Registered... ");
    }
}
