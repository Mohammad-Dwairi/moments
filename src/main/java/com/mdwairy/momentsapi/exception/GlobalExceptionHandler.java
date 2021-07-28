package com.mdwairy.momentsapi.exception;

import com.mdwairy.momentsapi.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.mail.SendFailedException;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Value("${exception.user.exists}")
    private String userExistsMsg;
    @Value("${exception.email}")
    private String failedToSendEmailMsg;
    @Value("${exception.token.notfound}")
    private String confirmationTokenNotFoundMsg;
    @Value("${exception.token.alreadyconfirmed}")
    private String confirmationTokenAlreadyConfirmedMsg;
    @Value("${exception.token.expired}")
    private String confirmationTokenExpiredMsg;

    @ExceptionHandler(value = {UserAlreadyExistsException.class})
    public ResponseEntity<Object> userAlreadyExistsHandler() {
        return new ResponseEntity<>(JwtUtil.buildErrorMap(userExistsMsg), CONFLICT);
    }

    @ExceptionHandler(value = {SendFailedException.class})
    public ResponseEntity<Object> failedToSendConfirmationEmailHandler() {
        return new ResponseEntity<>(JwtUtil.buildErrorMap(failedToSendEmailMsg), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {ConfirmationTokenNotFoundException.class})
    public ResponseEntity<Object> tokenNotFoundHandler() {
        return new ResponseEntity<>(JwtUtil.buildErrorMap(confirmationTokenNotFoundMsg), UNAUTHORIZED);
    }

    @ExceptionHandler(value = {ConfirmationTokenAlreadyConfirmedException.class})
    public ResponseEntity<Object> confirmationTokenAlreadyConfirmed() {
        return new ResponseEntity<>(JwtUtil.buildErrorMap(confirmationTokenAlreadyConfirmedMsg), BAD_REQUEST);
    }

    @ExceptionHandler(value = {ConfirmationTokenExpiredException.class})
    public ResponseEntity<Object> confirmationTokenExpired() {
        return new ResponseEntity<>(JwtUtil.buildErrorMap(confirmationTokenExpiredMsg), UNAUTHORIZED);
    }

}
