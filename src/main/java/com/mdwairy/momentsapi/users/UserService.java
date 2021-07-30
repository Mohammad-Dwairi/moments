package com.mdwairy.momentsapi.users;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User register(User user);
    User getUserByEmail(String email);
    User getUserFromSecurityContext();
    void enableUser(String email);

}
