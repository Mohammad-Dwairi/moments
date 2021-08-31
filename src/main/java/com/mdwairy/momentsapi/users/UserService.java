package com.mdwairy.momentsapi.users;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> findAll();
    User findById(Long Id);
    User findByUsername(String username);
    User findByEmail(String email);

    void register(User user);
    User getUserFromSecurityContext();
    void enableUser(String username);

    @PreAuthorize("#username == authentication.name")
    void deleteByUsername(String username);

}
