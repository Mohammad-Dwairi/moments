package com.mdwairy.momentsapi.registration.tokens;

import com.mdwairy.momentsapi.exception.ConfirmationTokenAlreadyConfirmedException;
import com.mdwairy.momentsapi.exception.ConfirmationTokenExpiredException;
import com.mdwairy.momentsapi.exception.ConfirmationTokenNotFoundException;
import com.mdwairy.momentsapi.users.User;
import com.mdwairy.momentsapi.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {

    private final UserService userService;
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationToken save(ConfirmationToken confirmationToken) {
        return confirmationTokenRepository.save(confirmationToken);
    }

    public ConfirmationToken getNewConfirmationToken(User user) {
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setToken(generateToken());
        confirmationToken.setCreatedAt(LocalDateTime.now());
        confirmationToken.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        confirmationToken.setUser(user);
        return confirmationToken;
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new ConfirmationTokenNotFoundException("Confirmation Token not found"));

        setConfirmedAt(confirmationToken);
        userService.enableUser(confirmationToken.getUser().getEmail());
        return "Your email was successfully confirmed";
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    private void setConfirmedAt(ConfirmationToken confirmationToken) {
        if (confirmationToken.getConfirmedAt() != null) {
            throw new ConfirmationTokenAlreadyConfirmedException("Already Confirmed!");
        }
        else if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ConfirmationTokenExpiredException("Expired Confirmation Token");
        }
        confirmationTokenRepository.setConfirmedAt(confirmationToken.getToken(), LocalDateTime.now());
    }

}
