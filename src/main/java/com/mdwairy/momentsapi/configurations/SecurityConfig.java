package com.mdwairy.momentsapi.configurations;

import com.mdwairy.momentsapi.filter.AppAuthenticationFilter;
import com.mdwairy.momentsapi.filter.AppAuthorizationFilter;
import com.mdwairy.momentsapi.filter.AppAuthenticationEntryPoint;
import com.mdwairy.momentsapi.handler.AppAuthenticationFailureHandler;
import com.mdwairy.momentsapi.handler.AppAuthenticationSuccessHandler;
import com.mdwairy.momentsapi.jwt.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final JWTService jwtService;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.authorizeRequests().antMatchers("/registration/**", "/token/**", "/email", "/users/**", "/h2-console/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
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

    private AppAuthenticationFilter getAuthenticationFilter() throws Exception {
        return new AppAuthenticationFilter(authenticationManagerBean(), authenticationSuccessHandler(), authenticationFailureHandler());
    }

    private AppAuthorizationFilter getAuthorizationFilter() {
        return new AppAuthorizationFilter(jwtService);
    }

}
