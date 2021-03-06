package com.mdwairy.momentsapi.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdwairy.momentsapi.jwt.JWTResponse;
import com.mdwairy.momentsapi.jwt.JWTService;
import com.mdwairy.momentsapi.users.UserPrincipal;
import com.mdwairy.momentsapi.users.UserSecurity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
public class AppAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JWTService jwtService;
    private final UserSecurity userSecurity;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        log.info("AUTHENTICATION SUCCESS HANDLER");

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String username = userPrincipal.getUsername();

        if (userSecurity.isUserBlacklisted(username)) {
            userSecurity.removeUserFromBlacklist(username);
        }

        String accessToken = jwtService.getAccessToken(userPrincipal);
        String refreshToken = jwtService.getRefreshToken(userPrincipal);
        JWTResponse jwtResponse = jwtService.buildJWTResponse(userPrincipal.getUsername(), accessToken, refreshToken);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), jwtResponse);
    }
}
