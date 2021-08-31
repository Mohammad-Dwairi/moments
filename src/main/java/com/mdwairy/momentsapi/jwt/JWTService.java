package com.mdwairy.momentsapi.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mdwairy.momentsapi.users.UserPrincipal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.mdwairy.momentsapi.constant.JWTConstant.*;

@Service
public class JWTService {

    private final Algorithm algorithm;

    public JWTService(@Value("${spring.mail.password}") String secret) {
        this.algorithm = Algorithm.HMAC256(secret.getBytes());
    }

    public String getAccessToken(UserPrincipal userPrincipal) {
        List<String> claims = this.getUserAuthorities(userPrincipal);

        return JWT.create()
                .withSubject(userPrincipal.getUsername())
                .withIssuer(MOMENTS_LLC)
                .withClaim(AUTHORITIES, claims)
                .withIssuedAt(getTimeAfter(0))
                .withExpiresAt(getTimeAfter(ACCESS_TOKEN_EXPIRATION_TIME))
                .sign(algorithm);
    }

    public String getRefreshToken(UserPrincipal userPrincipal) {
        return JWT.create()
                .withSubject(userPrincipal.getUsername())
                .withIssuer(MOMENTS_LLC)
                .withIssuedAt(getTimeAfter(0))
                .withExpiresAt(getTimeAfter(REFRESH_TOKEN_EXPIRATION_TIME))
                .sign(algorithm);
    }

    public JWTResponse buildJWTResponse(String username, String accessToken, String refreshToken) {
        return JWTResponse.builder()
                .username(username)
                .accessToken(accessToken)
                .accessTokenExpiresAt(getTimeAfter(ACCESS_TOKEN_EXPIRATION_TIME))
                .refreshToken(refreshToken)
                .build();
    }

    public String getUsernameFromToken(String token) {
        DecodedJWT decodedJWT = decodeToken(token);
        return decodedJWT.getSubject();
    }

    public String removeTokenBearerPrefix(String token) {
        return token.substring(TOKEN_PREFIX.length());
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        String username = getUsernameFromToken(token);
        List<SimpleGrantedAuthority> authorities = this.getAuthoritiesFromToken(token);
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    private DecodedJWT decodeToken(String token) {
        JWTVerifier verifier = this.getJWTVerifier();
        return verifier.verify(token);
    }

    private List<String> getUserAuthorities(UserPrincipal userPrincipal) {
        return userPrincipal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    private List<SimpleGrantedAuthority> getAuthoritiesFromToken(String token) {
        return decodeToken(token).getClaim(AUTHORITIES).asList(SimpleGrantedAuthority.class);
    }

    private JWTVerifier getJWTVerifier() {
        return JWT.require(algorithm).withIssuer(MOMENTS_LLC).build();
    }

    private Date getTimeAfter(long minutes) {
        return new Date(System.currentTimeMillis() + minutes * 60 * 1000);
    }

}
