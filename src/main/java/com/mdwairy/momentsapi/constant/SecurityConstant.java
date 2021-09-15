package com.mdwairy.momentsapi.constant;

public abstract class SecurityConstant {
    public static final String[] PUBLIC_ENDPOINTS = {"/login", "/registration/**", "/token/**", "/email", "/h2-console/**"};
    public static final String[] OWNER_RESTRICTED_ENDPOINTS = {"/users/{username}/**", "/{username}/work/**", "/{username}/education/**", "/{username}/pictures/**", "/{username}/info/**"};
    public static final String USER_ENDPOINTS_SPEL_ACCESS = "@userSecurity.checkOwnership(#username) or hasRole('ADMIN') or @userSecurity.isSafeHttpRequest()";
}
