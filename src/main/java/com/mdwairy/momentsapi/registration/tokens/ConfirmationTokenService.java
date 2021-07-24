package com.mdwairy.momentsapi.registration.tokens;

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
                .orElseThrow(() -> new IllegalStateException("Token not found"));
    }

    public ConfirmationToken save(ConfirmationToken confirmationToken) {
        return confirmationTokenRepository.save(confirmationToken);
    }

    public void setConfirmedAt(String token) {
        confirmationTokenRepository.setConfirmedAt(token, LocalDateTime.now());
    }
}
