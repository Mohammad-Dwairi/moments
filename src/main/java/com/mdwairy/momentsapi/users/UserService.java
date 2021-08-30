package com.mdwairy.momentsapi.users;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> findAll();
    User findById(Long Id);
    User findByEmail(String email);

    void register(User user);
    User getUserFromSecurityContext();
    void enableUser(String email);

    void deleteByUsername(String username);
}
