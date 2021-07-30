package com.mdwairy.momentsapi.email;

import com.mdwairy.momentsapi.registration.tokens.ConfirmationToken;
import com.mdwairy.momentsapi.registration.tokens.ConfirmationTokenService;
import com.mdwairy.momentsapi.users.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final ConfirmationTokenService confirmationTokenService;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Async
    public void send(User user) throws MessagingException {
        ConfirmationToken confirmationToken = confirmationTokenService.getNewConfirmationToken(user);
        ConfirmationToken savedCT = confirmationTokenService.save(confirmationToken);
        String link = "http://localhost:8080/registration/confirm?token="+savedCT.getToken();
        String emailBody = EmailBuilder.buildEmail(user.getFirstName(), link);
        MimeMessage mimeMessage = buildMimeMessage(user.getEmail(), emailBody);
        javaMailSender.send(mimeMessage);
    }

    private MimeMessage buildMimeMessage(String to, String email) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setText(email, true);
        helper.setTo(to);
        helper.setSubject("Confirm your email");
        helper.setFrom(senderEmail);
        return mimeMessage;
    }
}
