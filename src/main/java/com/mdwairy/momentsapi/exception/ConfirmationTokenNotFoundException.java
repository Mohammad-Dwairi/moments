package com.mdwairy.momentsapi.exception;

public class ConfirmationTokenNotFoundException extends RuntimeException {
    public ConfirmationTokenNotFoundException(String message) {
        super(message);
    }
}
