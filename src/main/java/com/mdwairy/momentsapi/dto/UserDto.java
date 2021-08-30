package com.mdwairy.momentsapi.dto;

import com.mdwairy.momentsapi.users.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private UserRole role;
    private Long detailsId;
}
