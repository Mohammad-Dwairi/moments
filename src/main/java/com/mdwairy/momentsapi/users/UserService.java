package com.mdwairy.momentsapi.users;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {

    List<User> findAll();
    List<User> findAllByUsernameIn(Set<String> usernames);
    User findById(Long Id);
    User findByUsername(String username);
    User findByEmail(String email);
    User getUserFromSecurityContext();

    void register(User user);

    void enableUser(String username);
    void disableUser(String username);

    void updateFirstName(String username, String firstname);
    void updateLastName(String username, String lastName);
    void updateUsername(String username, String newUsername, String accessToken);
    void updatePassword(String username,String oldPassword, String newPassword);

    @PreAuthorize("hasRole('ADMIN')")
    void updateUserRole(String username, String role);

    void deleteByUsername(String username);

}
