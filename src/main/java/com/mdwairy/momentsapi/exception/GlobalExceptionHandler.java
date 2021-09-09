package com.mdwairy.momentsapi.exception;

import com.mdwairy.momentsapi.constant.AppExceptionMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.mail.MessagingException;
import javax.validation.ConstraintViolationException;

import static com.mdwairy.momentsapi.constant.AppExceptionMessage.INVALID_HTTP_MESSAGE;
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

    @ExceptionHandler(value = {JWTException.class})
    public ResponseEntity<Object> jwtException(JWTException e) {
        errorResponse.setStatus(BAD_REQUEST.value());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTimestamp(currentTimeMillis());
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(value = {InvalidJsonKeyException.class, InvalidJsonValueException.class})
    public ResponseEntity<Object> invalidJsonKeyExceptionHandler(RuntimeException e) {
        errorResponse.setStatus(BAD_REQUEST.value());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTimestamp(currentTimeMillis());
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Object> notFoundExceptionHandler(ResourceNotFoundException e) {
        errorResponse.setStatus(NOT_FOUND.value());
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTimestamp(currentTimeMillis());
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<Object> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e) {
        errorResponse.setMessage(INVALID_HTTP_MESSAGE);
        errorResponse.setTimestamp(currentTimeMillis());
        errorResponse.setStatus(BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class, ConstraintViolationException.class})
    public ResponseEntity<Object> httpRequestMethodNotSupportedExceptionHandler(RuntimeException e) {
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTimestamp(currentTimeMillis());
        errorResponse.setStatus(BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

}
