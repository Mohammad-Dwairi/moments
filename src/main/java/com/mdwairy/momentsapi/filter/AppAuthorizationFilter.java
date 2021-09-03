package com.mdwairy.momentsapi.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdwairy.momentsapi.exception.AppErrorResponse;
import com.mdwairy.momentsapi.jwt.JWTService;
import com.mdwairy.momentsapi.users.UserSecurity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import static com.mdwairy.momentsapi.constant.JWTConstant.TOKEN_PREFIX;
import static com.mdwairy.momentsapi.constant.SecurityConstant.PUBLIC_ENDPOINTS;
import static com.mdwairy.momentsapi.constant.SecurityExceptionMessage.REVOKED_TOKEN;
import static java.lang.System.currentTimeMillis;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
public class AppAuthorizationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserSecurity userSecurity;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String servletPath = request.getServletPath();
        try {
            if (!Arrays.asList(PUBLIC_ENDPOINTS).contains(servletPath)) {
                String authorizationHeader = request.getHeader(AUTHORIZATION);
                if (isCorrectAuthorizationHeader(authorizationHeader)) {
                    authorizeUser(authorizationHeader);
                }
            }
            filterChain.doFilter(request, response);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            SecurityContextHolder.clearContext();
            response.setStatus(FORBIDDEN.value());
            response.setContentType(APPLICATION_JSON_VALUE);
            AppErrorResponse errorResponse = new AppErrorResponse(FORBIDDEN.value(), e.getMessage(), currentTimeMillis());
            new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
        }
    }

    private void checkUsersBlacklist(String username, String token) {
        if (userSecurity.isUserBlacklisted(username)) {
            jwtService.addTokenToBlacklist(token);
            userSecurity.removeUserFromBlacklist(username);
            throw new AccessDeniedException(REVOKED_TOKEN);
        }
    }

    private void checkTokensBlacklist(String token) {
        if (jwtService.isTokenInBlacklist(token)) {
            throw new AccessDeniedException(REVOKED_TOKEN);
        }
    }

    private boolean isCorrectAuthorizationHeader(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX);
    }

    private void authorizeUser(String authorizationHeader) {
        String token = jwtService.removeTokenBearerPrefix(authorizationHeader);
        UsernamePasswordAuthenticationToken authenticationToken = jwtService.getAuthentication(token);
        String username = authenticationToken.getName();
        checkTokensBlacklist(token);
        checkUsersBlacklist(username, token);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
}
