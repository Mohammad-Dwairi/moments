package com.mdwairy.momentsapi.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdwairy.momentsapi.jwt.JWTResponse;
import com.mdwairy.momentsapi.jwt.JWTService;
import com.mdwairy.momentsapi.users.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
public class AppAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final JWTService jwtService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException {
        UserPrincipal userPrincipal = (UserPrincipal) authResult.getPrincipal();
        String accessToken = jwtService.getAccessToken(userPrincipal);
        String refreshToken = jwtService.getRefreshToken(userPrincipal);
        JWTResponse jwtResponse = jwtService.buildJWTResponse(userPrincipal.getUsername(), accessToken, refreshToken);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), jwtResponse);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        authenticationFailureHandler.onAuthenticationFailure(request, response, failed);
        //super.unsuccessfulAuthentication(request, response, failed);
    }
}
