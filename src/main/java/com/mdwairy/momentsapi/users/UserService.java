package com.mdwairy.momentsapi.users;

import com.mdwairy.momentsapi.registration.RegistrationRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User register(RegistrationRequest request);
}
