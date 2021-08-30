package com.mdwairy.momentsapi.constant;

public abstract class UserExceptionMessage {
    public static final String USER_NOT_FOUND_BY_EMAIL = "No user found with email (%s)";
    public static final String USER_NOT_FOUND_BY_ID = "No user found with id (%s)";
    public static final String USER_ALREADY_REGISTERED = "A user with this email is already registered";
}
