package com.mdwairy.momentsapi.users;

import com.mdwairy.momentsapi.app.userdetails.AppUserDetailsRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.mdwairy.momentsapi.constant.UserExceptionMessage.USER_NOT_FOUND_BY_USERNAME;
import static com.mdwairy.momentsapi.constant.UserExceptionMessage.USER_NOT_FOUND_BY_ID;


@Service
@Transactional
public class UserServiceJPA implements UserService {

    private final UserRepository userRepository;
    private final AppUserDetailsRepository detailsRepository;


    public UserServiceJPA(UserRepository userRepository, AppUserDetailsRepository detailsRepository) {
        this.userRepository = userRepository;
        this.detailsRepository = detailsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new UserPrincipal(user);
        } else {
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_BY_USERNAME, username));
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
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_BY_ID, id)));
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_BY_USERNAME, username));
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_BY_USERNAME, email));
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
    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }
}
