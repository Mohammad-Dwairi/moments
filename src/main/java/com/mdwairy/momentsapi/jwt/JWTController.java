package com.mdwairy.momentsapi.jwt;

import com.mdwairy.momentsapi.users.User;
import com.mdwairy.momentsapi.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RestController
@RequestMapping(path = "/token")
@RequiredArgsConstructor
public class JWTController {

    private final UserService userService;
    private final JWTService jwtService;

    @PostMapping("/refresh")
    public JWTResponse refreshToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        String refreshToken = authorizationHeader.substring("Bearer ".length());
        String username = jwtService.decodeToken(refreshToken).getName();
        User user = userService.getUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        String issuer = request.getRequestURL().toString();
        String accessToken = jwtService.getAccessToken(user, issuer);
        return jwtService.buildJWTResponse(accessToken, refreshToken);
    }
}
