package com.mdwairy.momentsapi.exception;

import com.mdwairy.momentsapi.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Value("${exception.user.exists}")
    private String userExistsMsg;

    @ExceptionHandler(value = {UserAlreadyExistsException.class})
    public ResponseEntity<Object> userAlreadyExistsHandler() {
        return new ResponseEntity<>(JwtUtil.buildErrorMap(userExistsMsg), HttpStatus.CONFLICT);
    }
}
