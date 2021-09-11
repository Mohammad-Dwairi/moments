package com.mdwairy.momentsapi.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mdwairy.momentsapi.exception.JWTException;
import com.mdwairy.momentsapi.users.UserPrincipal;
import com.mdwairy.momentsapi.users.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.mdwairy.momentsapi.constant.JWTConstant.*;
import static com.mdwairy.momentsapi.constant.SecurityExceptionMessage.INVALID_TOKEN;

@Slf4j
@Service
public class JWTService {

    private final Algorithm algorithm;
    private final JWTRepository jwtRepository;
    private final UserService userService;

    public JWTService(@Value("${spring.mail.password}") String secret, JWTRepository jwtRepository, @Lazy UserService userService) {
        this.algorithm = Algorithm.HMAC256(secret.getBytes());
        this.jwtRepository = jwtRepository;
        this.userService = userService;
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
        DecodedJWT decodedJWT = verifyToken(token);
        return decodedJWT.getSubject();
    }

    public String removeTokenBearerPrefix(String token) {
        if (token!= null && token.startsWith(TOKEN_PREFIX)) {
            return token.substring(TOKEN_PREFIX.length());
        }
        throw new JWTException(INVALID_TOKEN);
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        DecodedJWT verifiedToken = verifyToken(token);
        String username = verifiedToken.getSubject();
        UserPrincipal userPrincipal = (UserPrincipal) userService.loadUserByUsername(username);
        log.info("Email: {}", userPrincipal.getUser().getEmail());
        List<SimpleGrantedAuthority> authorities = this.getAuthoritiesFromToken(token);
        return new UsernamePasswordAuthenticationToken(userPrincipal, null, authorities);
    }

    public void addTokenToBlacklist(String accessToken) {
        jwtRepository.save(accessToken);
    }

    public boolean isTokenInBlacklist(String token) {
        return jwtRepository.isExists(token);
    }

    private DecodedJWT verifyToken(String token) {
        JWTVerifier verifier = this.getJWTVerifier();
        return verifier.verify(token);
    }

    private List<String> getUserAuthorities(UserPrincipal userPrincipal) {
        return userPrincipal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }

    private List<SimpleGrantedAuthority> getAuthoritiesFromToken(String token) {
        return verifyToken(token).getClaim(AUTHORITIES).asList(SimpleGrantedAuthority.class);
    }

    private JWTVerifier getJWTVerifier() {
        return JWT.require(algorithm).withIssuer(MOMENTS_LLC).build();
    }

    private Date getTimeAfter(long minutes) {
        return new Date(System.currentTimeMillis() + minutes * 60 * 1000);
    }

}
