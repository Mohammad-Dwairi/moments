package com.mdwairy.momentsapi.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserSecurity {

    private final HttpServletRequest httpServletRequest;

    public boolean checkOwnership(Authentication authentication, String username) {
        log.info("Authentication name: {}", authentication.getName());
        log.info("username: {}", username);
        log.info("AUTHORITIES: {}", authentication.getAuthorities());
        log.info("AUTHENTICATION DETAILS: {}", authentication.getDetails());
        log.info("AUTHENTICATION CREDENTIALS: {}", authentication.getCredentials());
        log.info("AUTHENTICATION PRINCIPAL: {}", authentication.getPrincipal());
        return authentication.getName().equals(username);
    }

    public boolean isSafeHttpRequest() {
        log.info(httpServletRequest.getMethod());
        log.info("IS USER IN ROLE_USER: {}", httpServletRequest.isUserInRole("ROLE_USER"));
        return httpServletRequest.getMethod().equals("GET");
    }

}
