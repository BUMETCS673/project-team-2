package com.soloSavings.serviceImpl;

import com.soloSavings.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendPasswordResetEmail(String toEmail, String resetToken) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("notification.solosavings@gmail.com");
        mailMessage.setTo(toEmail);
        mailMessage.setSubject("Reset Password with Solo-Savings");
        mailMessage.setText("To reset your password, click the link below:\n"
                + "http://localhost:8888/solosavings/reset-password?token=" + resetToken);
        System.out.println("I AM HERE!!!");

        javaMailSender.send(mailMessage);
    }
}
