package com.soloSavings.service;

import com.soloSavings.serviceImpl.PasswordResetService;
import com.soloSavings.utils.ResetTokenStorage;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PasswordResetServiceTest {
    @Mock
    ResetTokenStorage tokenStorage;
    @Mock
    EmailService emailService;
    @InjectMocks
    PasswordResetService passwordResetService;

    @Test
    public void testInitiatePasswordReset(){
        String username = "username";
        String userEmail = "useremail";

        doNothing().when(tokenStorage).storeToken(anyString(),anyString());
        doNothing().when(emailService).sendPasswordResetEmail(anyString(),anyString());
        passwordResetService.initiatePasswordReset(username,userEmail);

        verify(tokenStorage,times(1)).storeToken(anyString(),anyString());
        verify(emailService).sendPasswordResetEmail(anyString(),anyString());
    }

    @Test
    public void testRetrieveUsername(){
        String token = "token";
        String username = "username";

        when(tokenStorage.retrieveUsername(anyString())).thenReturn(username);
        String actual = passwordResetService.retrieveUserName(token);

        verify(tokenStorage,times(1)).retrieveUsername(anyString());
        assertEquals(username,actual);
    }

    @Test
    public void testDeleteTokenStorageRecord(){
        String username = "username";

        doNothing().when(tokenStorage).removeTokenRecord(anyString());
        passwordResetService.deleteTokenStorageRecord(username);

        verify(tokenStorage,times(1)).removeTokenRecord(anyString());
    }
}