package com.mdwairy.momentsapi.users;

import com.mdwairy.momentsapi.userinfo.UserInfoRepository;
import com.mdwairy.momentsapi.exception.InvalidJsonValueException;
import com.mdwairy.momentsapi.jwt.JWTService;
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
import java.util.Set;

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
    private final UserInfoRepository userInfoRepository;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserSecurity userSecurity;

    public UserServiceJPA(UserRepository userRepository, UserInfoRepository userInfoRepository,
                          JWTService jwtService, @Lazy PasswordEncoder passwordEncoder, UserSecurity userSecurity) {
        this.userRepository = userRepository;
        this.userInfoRepository = userInfoRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.userSecurity = userSecurity;
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
    public List<User> findAllByUsernameIn(Set<String> usernames) {
        return userRepository.findAllByUsernameIn(usernames);
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
        userInfoRepository.save(user.getUserInfo());
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
    public void updateUsername(String username, String newUsername, String accessToken) {
        accessToken = jwtService.removeTokenBearerPrefix(accessToken);
        userRepository.updateUserName(username, newUsername);
        jwtService.addTokenToBlacklist(accessToken);
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
        else {
            throw new UsernameNotFoundException(USER_NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public void updateUserRole(String username, String role) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getRole() == ROLE_ADMIN && !user.getUsername().equals(username)) {
                throw new AccessDeniedException("Not permitted");
            }
            if (role.equals("ADMIN")) {
                user.setRole(ROLE_ADMIN);
            } else if (role.equals("USER")) {
                user.setRole(ROLE_USER);
            } else {
                throw new InvalidJsonValueException(INVALID_ROLE);
            }
            userRepository.save(user);
            userSecurity.addUserToBlacklist(username);
        }
        else {
            throw new UsernameNotFoundException(USER_NOT_FOUND);
        }
    }

    @Override
    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }
}
