package com.mdwairy.momentsapi.registration.tokens;

import com.mdwairy.momentsapi.exception.ConfirmationTokenAlreadyConfirmedException;
import com.mdwairy.momentsapi.exception.ConfirmationTokenExpiredException;
import com.mdwairy.momentsapi.exception.ConfirmationTokenNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationToken findByToken(String token) {
        return confirmationTokenRepository
                .findByToken(token)
                .orElseThrow(ConfirmationTokenNotFoundException::new);
    }

    public ConfirmationToken save(ConfirmationToken confirmationToken) {
        return confirmationTokenRepository.save(confirmationToken);
    }

    public void setConfirmedAt(ConfirmationToken confirmationToken) {
        if (confirmationToken.getConfirmedAt() != null) {
            throw new ConfirmationTokenAlreadyConfirmedException();
        }
        else if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new ConfirmationTokenExpiredException();
        }
        confirmationTokenRepository.setConfirmedAt(confirmationToken.getToken(), LocalDateTime.now());
    }
}
