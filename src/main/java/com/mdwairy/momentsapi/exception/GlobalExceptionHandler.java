package com.mdwairy.momentsapi.exception;

import com.mdwairy.momentsapi.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.mail.SendFailedException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Value("${exception.user.exists}")
    private String userExistsMsg;

    @Value("${exception.email}")
    private String failedToSendEmailMsg;

    @ExceptionHandler(value = {UserAlreadyExistsException.class})
    public ResponseEntity<Object> userAlreadyExistsHandler() {
        return new ResponseEntity<>(JwtUtil.buildErrorMap(userExistsMsg), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {SendFailedException.class})
    public ResponseEntity<Object> failedToSendConfirmationEmailHandler() {
        return new ResponseEntity<>(JwtUtil.buildErrorMap(failedToSendEmailMsg), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
