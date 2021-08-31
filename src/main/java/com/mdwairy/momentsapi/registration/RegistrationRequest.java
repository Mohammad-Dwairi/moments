package com.mdwairy.momentsapi.registration;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegistrationRequest {

    @NotNull
    @Size(min = 2, max = 30, message = "Username Should be between 2 and 30 characters")
    private String username;

    @NotNull
    @Size(min = 2, max = 15, message = "First Name Should be between 2 and 15 characters")
    private String firstName;

    @NotNull
    @Size(min = 2, max = 15, message = "Last Name Should be between 2 and 15 characters")
    private String lastName;

    @NotNull
    @NotEmpty
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotNull
    @Size(min = 5, max = 30, message = "Password should be between 5 and 30 characters")
    private String password;

}
