package com.mdwairy.momentsapi.jwt;

import com.mdwairy.momentsapi.users.UserRole;
import lombok.*;


@Data
@Builder
public class JWTResponse {

    private String firstName;
    private String lastName;
    private String email;
    private UserRole role;
    private String accessToken;
    private String refreshToken;
}
