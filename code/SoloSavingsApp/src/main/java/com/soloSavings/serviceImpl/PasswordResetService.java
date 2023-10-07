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

    public boolean isTokenValid(String token) {
        String userName = tokenStorage.retrieveUsername(token);
        System.out.println(userName);
        return userName != null;
    }

    public String retrieveUserName(String token) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        tokenStorage.printTokenMap();
        String userName = tokenStorage.retrieveUsername(token);
        System.out.println(userName);
        return userName;
    }
//
//    public void resetPassword(String token, String newPassword) {
//        // Retrieve the user ID associated with the token
//        Long userId = tokenStorage.retrieveUserId(token);
//
//        if (userId != null) {
//            // Reset the user's password using the new password
//            resetUserPassword(userId, newPassword);
//
//            // Remove the token from memory (since it has been used)
//            tokenStorage.removeToken(token);
//        }
//    }

    // Other methods for generating tokens, sending emails, and resetting passwords
}