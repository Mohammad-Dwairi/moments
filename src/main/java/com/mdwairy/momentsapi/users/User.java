package com.mdwairy.momentsapi.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mdwairy.momentsapi.app.userdetails.AppUserDetails;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 20, message = "First name must be between 2 and 20 characters")
    protected String firstName;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 20, message = "Last name must be between 2 and 20 characters")
    protected String lastName;

    @NotNull
    @NotEmpty
    @Size(min = 2, max = 30, message = "Username must be between 2 and 30 characters")
    @Column(unique = true, nullable = false)
    protected String username;

    @Email(message = "Invalid email address")
    @Column(unique = true, nullable = false)
    protected String email;

    @NotNull
    @NotEmpty
    @Size(min = 5, message = "Password must be at least 5 characters")
    private String password;

    private Boolean isAccountLocked = false;

    private Boolean isAccountEnabled = false;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToOne
    private AppUserDetails appUserDetails = new AppUserDetails();

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

}
