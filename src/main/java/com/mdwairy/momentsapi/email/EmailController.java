package com.mdwairy.momentsapi.email;

import com.mdwairy.momentsapi.users.User;
import com.mdwairy.momentsapi.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

import static com.mdwairy.momentsapi.constant.UserExceptionMessage.USER_NOT_FOUND_BY_EMAIL;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/email", produces = APPLICATION_JSON_VALUE)
public class EmailController {

    private final EmailService emailService;
    private final UserService userService;

    @GetMapping
    public String sendEmail(@RequestParam("email") String email) throws MessagingException {
        try {
            User user = userService.findByEmail(email);
            emailService.send(user);
            return "Email Sent";
        }
        catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException(String.format(USER_NOT_FOUND_BY_EMAIL, email));
        }
        catch (MessagingException e) {
            throw new MessagingException("Failed to send the confirmation email");
        }
    }

}
