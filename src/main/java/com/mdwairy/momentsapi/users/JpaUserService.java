package com.mdwairy.momentsapi.users;

import com.mdwairy.momentsapi.registration.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserService implements UserService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final String ERR_MSG = "User with email %s could not be found";
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(ERR_MSG, email)));
    }

    @Override
    public User register(RegistrationRequest request) {
        return null;
    }
}
