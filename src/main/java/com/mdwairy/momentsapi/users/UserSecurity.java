package com.mdwairy.momentsapi.users;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserSecurity {

    private final HttpServletRequest httpServletRequest;
    private final RedisTemplate<String, String> redisTemplate;
    public static final String USERS_SET_NAME = "users";

    public boolean checkOwnership(String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
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

    public UserPrincipal getUserPrinciple() {
        return (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public void addUserToBlacklist(String username) {
        redisTemplate.opsForSet().add(USERS_SET_NAME, username);
    }

    public void removeUserFromBlacklist(String username) {
        redisTemplate.opsForSet().remove(USERS_SET_NAME, username);
    }

    public boolean isUserBlacklisted(String username) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(USERS_SET_NAME, username));
    }

}
