package com.mdwairy.momentsapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationTokenAlreadyConfirmedException extends RuntimeException {
    private String message;
}
