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

import static com.mdwairy.momentsapi.constant.UserExceptionMessage.USER_NOT_FOUND_BY_EMAIL;
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new UserPrincipal(user);
        } else {
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_BY_EMAIL, email));
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
    public User findByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_BY_EMAIL, email));
    }

    @Override
    public void register(@Valid User user) {
        detailsRepository.save(user.getAppUserDetails());
        userRepository.save(user);
    }

    @Override
    public User getUserFromSecurityContext() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getPrincipal().toString();
        return this.findByEmail(email);
    }

    @Override
    public void enableUser(String email) {
        userRepository.enableUser(email);
    }

    @Override
    public void deleteByUsername(String username) {
        userRepository.deleteByEmail(username);
    }
}
