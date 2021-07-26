package com.mdwairy.momentsapi.jwt;

import com.mdwairy.momentsapi.users.User;
import com.mdwairy.momentsapi.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RestController
@RequestMapping(path = "/token")
@RequiredArgsConstructor
public class JWTController {

    private final UserService userService;

    @PostMapping("/refresh")
    public Map<String, String> refreshToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        String refreshToken = authorizationHeader.substring("Bearer ".length());
        String username = JwtUtil.decodeToken(refreshToken).getName();
        User user = userService.getUserByEmail(username);
        String accessToken = JwtUtil.generateAccessToken(user, request.getRequestURL().toString());
        return JwtUtil.buildTokensMap(accessToken, refreshToken);
    }
}
