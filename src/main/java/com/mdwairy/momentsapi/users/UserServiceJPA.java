package com.mdwairy.momentsapi.users;

import com.mdwairy.momentsapi.app.userdetails.AppUserDetailsRepository;
import com.mdwairy.momentsapi.app.userdetails.AppUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;


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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final String ERR_MSG = "User with email %s could not be found";
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(ERR_MSG, email)));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserFromSecurityContext() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getPrincipal().toString();
        User user = getUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return getUserByEmail(username);
    }

    @Override
    public void register(@Valid User user) {
        detailsRepository.save(user.getAppUserDetails());
        userRepository.save(user);
    }

    @Override
    public void enableUser(String email) {
        userRepository.enableUser(email);
    }

    @Override
    public User findById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public void deleteByUsername(String username) {
        userRepository.deleteByEmail(username);
    }
}
