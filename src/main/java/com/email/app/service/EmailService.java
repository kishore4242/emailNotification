package com.email.app.service;

import com.email.app.model.Confirmation;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.constraints.Email;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
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

    public void sendMail(String name, @Email String to, String token) {
        sendMailFormat(name,to,token);
    }

    public void sendReverification(String name, @Email String email, String referenceById) {
        sendMailFormat(name,email,referenceById);
    }

    public void sendMailWithHtml(String name, @Email String toEmail) {
        MimeMessage helper = mailSender.createMimeMessage();
        String htmlMessage = """
                 <h2>Hello Kishore!</h2>
                 <p>This is an <b>HTML email</b> from my Java backend.</p>
                 <a href='https://google.com'>Click here</a>
                 """;
        try {
            MimeMessageHelper message = new MimeMessageHelper(helper);
            message.setTo(toEmail);
            message.setFrom(this.fromEmail);
            message.setSubject("This is the Mail with Html");
            message.setText(htmlMessage,true);
            mailSender.send(helper);
        }
        catch (MessagingException e){
            System.out.println(e.getMessage());
        }
    }
}
