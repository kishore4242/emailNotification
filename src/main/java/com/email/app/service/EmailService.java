package com.email.app.service;

import com.email.app.model.Confirmation;
import jakarta.validation.constraints.Email;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final String fromEmail;
    private final String hostname;

    public EmailService(JavaMailSender mailSender,
                        @Value("${spring.mail.username}") String fromEmail,
                        @Value("${spring.mail.verify.host}") String hostname) {
        this.mailSender = mailSender;
        this.fromEmail = fromEmail;
        this.hostname = hostname;
    }
    private synchronized void sendMailFormat(String name,String to, String token){
        try {
            String verifyLink = hostname + "/api/verify?token=" + token;

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setSubject("Email Verification for " + name);
            simpleMailMessage.setFrom(fromEmail);
            simpleMailMessage.setTo(to);
            simpleMailMessage.setText("Hi " + name + ",\n\nPlease verify your email by clicking the link:\n" + verifyLink);

            mailSender.send(simpleMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public void sendMail(String name, String to, String token) {
        sendMailFormat(name,to,token);
    }

    public void sendReverification(String name, @Email String email, String referenceById) {
        sendMailFormat(name,email,referenceById);
    }
}
