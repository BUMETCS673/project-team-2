package com.soloSavings.serviceImpl;
import com.soloSavings.service.EmailService;
import com.soloSavings.utils.ResetTokenStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private final ResetTokenStorage tokenStorage;
    @Autowired
    private final EmailService emailService;

    @Autowired
    public PasswordResetService(ResetTokenStorage tokenStorage, EmailService emailService) {
        this.tokenStorage = tokenStorage;
        this.emailService = emailService;
    }

    public void initiatePasswordReset(String userName, String userEmail) {
        String resetToken = UUID.randomUUID().toString();
        tokenStorage.storeToken(userName, resetToken);
        emailService.sendPasswordResetEmail(userEmail, resetToken);
    }

    public String retrieveUserName(String token) {
        String userName = tokenStorage.retrieveUsername(token);
        return userName;
    }

    public void deleteTokenStorageRecord(String userName) {
        tokenStorage.removeTokenRecord(userName);
    }
}