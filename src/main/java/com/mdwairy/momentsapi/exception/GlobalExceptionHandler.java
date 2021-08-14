package com.mdwairy.momentsapi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.mail.MessagingException;

import static java.lang.System.currentTimeMillis;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final AppErrorResponse errorResponse = new AppErrorResponse();

    @ExceptionHandler(value = {UserAlreadyExistsException.class})
    public ResponseEntity<Object> userAlreadyExistsHandler(UserAlreadyExistsException e) {
        errorResponse.setStatus(CONFLICT.value());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTimestamp(currentTimeMillis());
        return new ResponseEntity<>(errorResponse, CONFLICT);
    }

    @ExceptionHandler(value = {MessagingException.class})
    public ResponseEntity<Object> failedToSendConfirmationEmailHandler(MessagingException e) {
        errorResponse.setStatus(INTERNAL_SERVER_ERROR.value());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTimestamp(currentTimeMillis());
        return new ResponseEntity<>(errorResponse, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {ConfirmationTokenNotFoundException.class})
    public ResponseEntity<Object> tokenNotFoundHandler(ConfirmationTokenNotFoundException e) {
        errorResponse.setStatus(UNAUTHORIZED.value());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTimestamp(currentTimeMillis());
        return new ResponseEntity<>(errorResponse, UNAUTHORIZED);
    }

    @ExceptionHandler(value = {ConfirmationTokenAlreadyConfirmedException.class})
    public ResponseEntity<Object> confirmationTokenAlreadyConfirmed(ConfirmationTokenAlreadyConfirmedException e) {
        errorResponse.setStatus(BAD_REQUEST.value());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTimestamp(currentTimeMillis());
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(value = {ConfirmationTokenExpiredException.class})
    public ResponseEntity<Object> confirmationTokenExpired(ConfirmationTokenExpiredException e) {
        errorResponse.setStatus(UNAUTHORIZED.value());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTimestamp(currentTimeMillis());
        return new ResponseEntity<>(errorResponse, UNAUTHORIZED);
    }

    @ExceptionHandler(value = {FriendRequestException.class})
    public ResponseEntity<Object> friendRequestsExceptionHandler(FriendRequestException e) {
        errorResponse.setStatus(BAD_REQUEST.value());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTimestamp(currentTimeMillis());
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity<Object> userDoesNotExistsExceptionHandler(UsernameNotFoundException e) {
        errorResponse.setStatus(NOT_FOUND.value());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTimestamp(currentTimeMillis());
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

}
