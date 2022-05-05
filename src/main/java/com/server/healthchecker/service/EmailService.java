package com.server.healthchecker.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    private final JavaMailSender emailSender;
    private final String email;

    public EmailService(JavaMailSender emailSender,
                        @Value("${email.sender.username}") String email) {
        this.emailSender = emailSender;
        this.email = email;
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(email);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
        }catch (Exception ex){
            // silent exception , so it won't break the logic
            log.info("Email Error : " + ex.getMessage());
        }
    }
}
