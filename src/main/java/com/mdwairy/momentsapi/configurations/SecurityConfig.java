package com.mdwairy.momentsapi.configurations;

import com.mdwairy.momentsapi.filter.AppAuthenticationFilter;
import com.mdwairy.momentsapi.filter.AppAuthorizationFilter;
import com.mdwairy.momentsapi.filter.AppAuthenticationEntryPoint;
import com.mdwairy.momentsapi.handler.AppAccessDeniedHandler;
import com.mdwairy.momentsapi.handler.AppAuthenticationFailureHandler;
import com.mdwairy.momentsapi.handler.AppAuthenticationSuccessHandler;
import com.mdwairy.momentsapi.jwt.JWTService;
import com.mdwairy.momentsapi.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.mdwairy.momentsapi.constant.SecurityConstant.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final JWTService jwtService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.authorizeRequests().antMatchers(PUBLIC_ENDPOINTS).permitAll();
        http.authorizeRequests().antMatchers(OWNER_RESTRICTED_ENDPOINTS).access(USER_ENDPOINTS_SPEL_ACCESS);
        //http.authorizeRequests().antMatchers(ADMIN_RESTRICTED_ENDPOINT).access("hasRole('ADMIN')");
        http.authorizeRequests().anyRequest().authenticated();
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());
        http.addFilter(getAuthenticationFilter());
        http.addFilterBefore(getAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement().sessionCreationPolicy(STATELESS);
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new AppAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AppAuthenticationSuccessHandler(jwtService);
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AppAuthenticationEntryPoint();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AppAccessDeniedHandler();
    }

    private AppAuthenticationFilter getAuthenticationFilter() throws Exception {
        return new AppAuthenticationFilter(authenticationManagerBean(), authenticationSuccessHandler(), authenticationFailureHandler(), userService);
    }

    private AppAuthorizationFilter getAuthorizationFilter() {
        return new AppAuthorizationFilter(jwtService);
    }

}
