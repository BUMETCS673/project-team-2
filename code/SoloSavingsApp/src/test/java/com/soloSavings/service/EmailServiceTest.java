package com.soloSavings.service;

import com.soloSavings.serviceImpl.EmailServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class EmailServiceTest {

    @InjectMocks
    EmailServiceImpl emailService;
    @Mock
    JavaMailSender javaMailSender;

    @Test
    public void testSendPasswordResetEmail(){
        doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));
        emailService.sendPasswordResetEmail("email","token");

        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}