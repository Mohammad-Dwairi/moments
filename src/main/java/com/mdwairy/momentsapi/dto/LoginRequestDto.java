package com.mdwairy.momentsapi.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String username = "";
    private String email = "";
    private String password = "";
}
