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
        mailMessage.setText("To reset your password, please copy paste the reset token to the reset password form:\n"
                + resetToken);

        javaMailSender.send(mailMessage);
    }
}
