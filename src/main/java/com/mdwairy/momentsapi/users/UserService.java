package com.mdwairy.momentsapi.users;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    void register(User user);
    User getUserByEmail(String email);
    List<User> findAll();
    User getUserFromSecurityContext();
    void enableUser(String email);
    User findById(Long Id);
    void deleteByUsername(String username);
}
