package com.mdwairy.momentsapi.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.mdwairy.momentsapi.users.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class JwtUtil {

    @Value("${spring.mail.password}")
    private final static String secret = "test";
    private final static Algorithm algorithm = Algorithm.HMAC256(secret.getBytes());

    public static String generateAccessToken(User user, String issuer) {
        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(getTimeAfter(10))
                .withIssuer(issuer)
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }

    public static String generateRefreshToken(User user, String issuer) {
        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(getTimeAfter(120))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public static Date getTimeAfter(int minutes) {
        return new Date(System.currentTimeMillis() * minutes * 60 * 1000);
    }

    public static Map<String, String> buildTokensMap (String accessToken, String refreshToken) {
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);
        return tokens;
    }
}
