package com.mdwairy.momentsapi.jwt;

import lombok.Builder;
import lombok.Data;

import java.util.Date;


@Data
@Builder
public class JWTResponse {
    private String accessToken;
    private String refreshToken;
    private Date accessTokenExpiresAt;
}
