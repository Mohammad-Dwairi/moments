package com.mdwairy.momentsapi.bootstrap;

import com.mdwairy.momentsapi.registration.RegistrationRequest;
import com.mdwairy.momentsapi.registration.RegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Profile("dev")
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final RegistrationService registrationService;

    @Override
    public void run(String... args) {
        RegistrationRequest request = new RegistrationRequest();
        request.setFirstName("Mohammad");
        request.setLastName("Dwairi");
        request.setEmail("mdwairy@gmail.com");
        request.setUsername("mohammad_dwairi");
        request.setPassword("12345");
        registrationService.register(request);
        log.info("User Registered... ");
    }
}
