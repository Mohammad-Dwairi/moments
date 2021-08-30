package com.mdwairy.momentsapi.users;

import static com.mdwairy.momentsapi.constant.Authority.ADMIN_AUTHORITIES;
import static com.mdwairy.momentsapi.constant.Authority.USER_AUTHORITIES;

public enum UserRole {

    ROLE_ADMIN(ADMIN_AUTHORITIES),
    ROLE_USER(USER_AUTHORITIES);

    private final String[] authorities;

    UserRole(String[] authorities) {
        this.authorities = authorities;
    }

    public String[] getAuthorities() {
        return this.authorities;
    }
}
