package com.mdwairy.momentsapi.constant;

public abstract class SecurityExceptionMessage {
    public static final String AUTHENTICATION_FAILED = "Invalid email or password";
    public static final String INVALID_TOKEN = "Invalid token";
    public static final String FORBIDDEN_REQUEST = "Authentication is required to access this resource";
    public static final String INVALID_ROLE = "Invalid User Role, Accepted Roles: ['ADMIN', 'USER']";
    public static final String INVALID_JSON_KEY = "Invalid JSON Key";
    public static final String REVOKED_TOKEN = "The token has been revoked due to a change in user credentials";
}
