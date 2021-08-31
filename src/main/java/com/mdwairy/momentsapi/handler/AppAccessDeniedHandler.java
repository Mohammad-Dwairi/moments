package com.mdwairy.momentsapi.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdwairy.momentsapi.exception.AppErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.System.currentTimeMillis;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class AppAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        AppErrorResponse errorResponse = new AppErrorResponse();
        errorResponse.setMessage(e.getMessage());
        errorResponse.setTimestamp(currentTimeMillis());
        errorResponse.setStatus(FORBIDDEN.value());

        response.setStatus(FORBIDDEN.value());
        response.setContentType(APPLICATION_JSON_VALUE);

        new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
    }
}
