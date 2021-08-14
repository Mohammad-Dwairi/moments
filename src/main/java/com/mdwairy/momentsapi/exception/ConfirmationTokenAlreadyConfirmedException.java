package com.mdwairy.momentsapi.exception;

public class ConfirmationTokenAlreadyConfirmedException extends RuntimeException {
    public ConfirmationTokenAlreadyConfirmedException(String message) {
        super(message);
    }
}
