package com.mdwairy.momentsapi.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Async
    public void send(String to, String email) throws SendFailedException {
        try {
            javaMailSender.send(buildMimeMessage(to, email));
        }
        catch (MailException | MessagingException e) {
            log.error(e.getMessage());
            throw new SendFailedException();
        }
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
