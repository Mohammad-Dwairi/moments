package com.mdwairy.momentsapi.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdwairy.momentsapi.constant.SecurityExceptionMessage;
import com.mdwairy.momentsapi.exception.AppErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.System.currentTimeMillis;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class AppAuthenticationEntryPoint extends Http403ForbiddenEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {

        AppErrorResponse errorResponse = new AppErrorResponse();
        errorResponse.setMessage(SecurityExceptionMessage.FORBIDDEN_REQUEST);
        errorResponse.setStatus(FORBIDDEN.value());
        errorResponse.setTimestamp(currentTimeMillis());

        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(FORBIDDEN.value());

        new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
    }
}
