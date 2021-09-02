package com.mdwairy.momentsapi.users;

import com.mdwairy.momentsapi.app.userdetails.AppUserDetailsRepository;
import com.mdwairy.momentsapi.exception.InvalidJsonKeyException;
import com.mdwairy.momentsapi.exception.InvalidJsonValueException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.mdwairy.momentsapi.constant.SecurityExceptionMessage.AUTHENTICATION_FAILED;
import static com.mdwairy.momentsapi.constant.SecurityExceptionMessage.INVALID_ROLE;
import static com.mdwairy.momentsapi.constant.UserExceptionMessage.USER_NOT_FOUND;
import static com.mdwairy.momentsapi.users.UserRole.ROLE_ADMIN;
import static com.mdwairy.momentsapi.users.UserRole.ROLE_USER;


@Slf4j
@Service
@Transactional
public class UserServiceJPA implements UserService {

    private final UserRepository userRepository;
    private final AppUserDetailsRepository detailsRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceJPA(UserRepository userRepository, AppUserDetailsRepository detailsRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.detailsRepository = detailsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new UserPrincipal(user);
        } else {
            throw new UsernameNotFoundException(USER_NOT_FOUND);
        }
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }


    @Override
    public User findById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND));
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new UsernameNotFoundException(USER_NOT_FOUND);
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new UsernameNotFoundException(USER_NOT_FOUND);
    }

    @Override
    public void register(@Valid User user) {
        detailsRepository.save(user.getAppUserDetails());
        userRepository.save(user);
    }

    @Override
    public User getUserFromSecurityContext() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getPrincipal().toString();
        return this.findByUsername(username);
    }

    @Override
    public void enableUser(String username) {
        userRepository.enableUser(username);
    }

    @Override
    public void disableUser(String username) {
        userRepository.disableUser(username);
    }

    @Override
    public void updateFirstName(String username, String firstname) {
        userRepository.updateFirstName(username, firstname);
    }

    @Override
    public void updateLastName(String username, String lastName) {
        userRepository.updateLastName(username, lastName);
    }

    @Override
    public void updateUsername(String username, String newUsername) {
        userRepository.updateUserName(username, newUsername);
    }

    @Override
    @Transactional
    public void updatePassword(String username, String oldPassword, String newPassword) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String dbPassword = user.getPassword();
            boolean match = passwordEncoder.matches(oldPassword, dbPassword);
            if (match) {
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
            } else {
                throw new AccessDeniedException(AUTHENTICATION_FAILED);
            }
        }
        throw new UsernameNotFoundException(USER_NOT_FOUND);
    }

    @Override
    @Transactional
    public void updateUserRole(String username, String role) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (role.equals("ADMIN")) {
                user.setRole(ROLE_ADMIN);
            } else if (role.equals("USER")) {
                user.setRole(ROLE_USER);
            } else {
                throw new InvalidJsonValueException(INVALID_ROLE);
            }
            userRepository.save(user);
        }
    }

    @Override
    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }
}
