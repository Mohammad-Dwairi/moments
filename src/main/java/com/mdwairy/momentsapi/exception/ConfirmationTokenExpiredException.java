package com.mdwairy.momentsapi.exception;

public class ConfirmationTokenExpiredException extends RuntimeException {

    public ConfirmationTokenExpiredException(String message) {
        super(message);
    }
}
