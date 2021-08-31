package com.mdwairy.momentsapi.constant;

public abstract class SecurityExceptionMessage {
    public static final String AUTHENTICATION_FAILED = "Invalid email or password";
    public static final String INVALID_TOKEN = "Invalid token";
    public static final String FORBIDDEN_REQUEST = "Authentication is required to access this resource";
}
