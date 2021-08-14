package com.mdwairy.momentsapi.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mdwairy.momentsapi.users.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JWTService {

    public static final int ACCESS_TOKEN_EXPIRES_WITHIN = 60;
    public static final int REFRESH_TOKEN_EXPIRES_WITHIN = 1000;

    private final Algorithm algorithm;

    public JWTService(@Value("${spring.mail.password}") String secret) {
        this.algorithm = Algorithm.HMAC256(secret.getBytes());
    }

    public String getAccessToken(User user, String issuer) {
        List<String> USER_ROLES = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return JWT.create()
                .withSubject(user.getEmail())
                .withIssuedAt(getTimeAfter(0))
                .withExpiresAt(getTimeAfter(ACCESS_TOKEN_EXPIRES_WITHIN))
                .withIssuer(issuer)
                .withClaim("roles", USER_ROLES)
                .sign(algorithm);
    }

    public String getRefreshToken(User user, String issuer) {
        return JWT.create()
                .withSubject(user.getEmail())
                .withExpiresAt(getTimeAfter(REFRESH_TOKEN_EXPIRES_WITHIN))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public UsernamePasswordAuthenticationToken decodeToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String username = decodedJWT.getSubject();
        Collection<SimpleGrantedAuthority> authorities = decodedJWT.getClaim("roles").asList(SimpleGrantedAuthority.class);
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    public JWTResponse buildJWTResponse(String accessToken, String refreshToken) {
        return JWTResponse.builder()
                .accessToken(accessToken)
                .accessTokenExpiresAt(getTimeAfter(ACCESS_TOKEN_EXPIRES_WITHIN))
                .refreshToken(refreshToken)
                .build();
    }

    private Date getTimeAfter(int minutes) {
        return new Date(System.currentTimeMillis() + (long) minutes * 60 * 1000);
    }

}
