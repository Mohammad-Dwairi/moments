package com.mdwairy.momentsapi.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdwairy.momentsapi.exception.AppErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.mdwairy.momentsapi.constant.SecurityExceptionMessage.AUTHENTICATION_FAILED;
import static java.lang.System.currentTimeMillis;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class AppAuthenticationFailureHandler implements AuthenticationFailureHandler {

    // If the user entered bad credentials
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e) throws IOException {

        log.error("AUTHENTICATION FAILURE HANDLER");

        AppErrorResponse errorResponse = new AppErrorResponse();
        errorResponse.setMessage(AUTHENTICATION_FAILED);
        errorResponse.setTimestamp(currentTimeMillis());
        errorResponse.setStatus(UNAUTHORIZED.value());

        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(UNAUTHORIZED.value());

        new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
    }
}
