package com.soloSavings.service;

public interface EmailService {
    public void sendPasswordResetEmail(String toEmail, String resetToken);
}
