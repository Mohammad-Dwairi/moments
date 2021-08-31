package com.mdwairy.momentsapi.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mdwairy.momentsapi.exception.AppErrorResponse;
import com.mdwairy.momentsapi.jwt.JWTService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.mdwairy.momentsapi.constant.JWTConstant.TOKEN_PREFIX;
import static com.mdwairy.momentsapi.constant.SecurityExceptionMessage.INVALID_TOKEN;
import static java.lang.System.currentTimeMillis;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
public class AppAuthorizationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!request.getServletPath().equals("/registration") && !request.getServletPath().equals("/login")) {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
                try {
                    String token = jwtService.removeTokenBearerPrefix(authorizationHeader);
                    UsernamePasswordAuthenticationToken authenticationToken = jwtService.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
                catch (RuntimeException e) {

                    log.error("AUTHORIZATION EXCEPTION");
                    log.error(e.getMessage());

                    SecurityContextHolder.clearContext();

                    response.setStatus(FORBIDDEN.value());
                    response.setContentType(APPLICATION_JSON_VALUE);

                    AppErrorResponse errorResponse = new AppErrorResponse();
                    errorResponse.setStatus(FORBIDDEN.value());
                    errorResponse.setTimestamp(currentTimeMillis());
                    errorResponse.setMessage(INVALID_TOKEN);

                    new ObjectMapper().writeValue(response.getOutputStream(), errorResponse);
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
