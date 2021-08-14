package com.mdwairy.momentsapi.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdwairy.momentsapi.exception.AppErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.System.currentTimeMillis;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
public class AppAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e) throws IOException {
        response.setStatus(UNAUTHORIZED.value());
        AppErrorResponse errorResponse = new AppErrorResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTimestamp(currentTimeMillis());
        errorResponse.setStatus(UNAUTHORIZED.value());
        new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);

    }
}
