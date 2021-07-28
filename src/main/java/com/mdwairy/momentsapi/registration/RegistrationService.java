package com.mdwairy.momentsapi.registration;

import com.mdwairy.momentsapi.email.EmailBuilder;
import com.mdwairy.momentsapi.email.EmailService;
import com.mdwairy.momentsapi.exception.UserAlreadyExistsException;
import com.mdwairy.momentsapi.registration.tokens.ConfirmationToken;
import com.mdwairy.momentsapi.registration.tokens.ConfirmationTokenService;
import com.mdwairy.momentsapi.users.User;
import com.mdwairy.momentsapi.users.UserRole;
import com.mdwairy.momentsapi.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final PasswordEncoder encoder;
    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;

    public User register(@Valid RegistrationRequest request) throws UserAlreadyExistsException, SendFailedException {
        if (isUserExists(request.getEmail())) {
            throw new UserAlreadyExistsException();
        }

        User user = mapRegistrationRequestToUser(request);
        User savedUser = userService.register(user);
        ConfirmationToken confirmationToken = createConfirmationToken(savedUser);
        String confirmationTokenString = confirmationTokenService.save(confirmationToken).getToken();
        sendConfirmationEmail(savedUser, confirmationTokenString);
        return savedUser;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.findByToken(token);

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        userService.enableUser(confirmationToken.getUser().getEmail());
        return "confirmed";
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

    private ConfirmationToken createConfirmationToken(User user) {
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setToken(generateToken());
        confirmationToken.setCreatedAt(LocalDateTime.now());
        confirmationToken.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        confirmationToken.setUser(user);
        return confirmationToken;
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    private void sendConfirmationEmail(User user, String confirmationToken) throws SendFailedException {
        String confirmationLink = "http://localhost:8080/registration/confirm?token=" + confirmationToken;
        emailService.send(user.getEmail(), EmailBuilder.buildEmail(user.getFirstName(), confirmationLink));
    }

}
