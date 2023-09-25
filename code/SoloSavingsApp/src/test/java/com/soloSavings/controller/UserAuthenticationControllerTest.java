package com.soloSavings.controller;

import com.soloSavings.exceptions.TransactionException;
import com.soloSavings.exceptions.UserAuthenticationException
import com.soloSavings.model.Transaction;
import com.soloSavings.model.helper.TransactionType;
import com.soloSavings.model.User;
import com.soloSavings.service.TransactionService;
import com.soloSavings.controller.UserAuthenticationController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest
public class TransactionControllerTest { 
    @Mock
    UserAuthenticationController userAuthController;

    @Test
    public void testRegisterUser() throws UserAuthenticationException {
        User registerUser = new User(1, "test", "test@solosavings.com", "hhhh", LocalDate.now(), 100.00, LocalDate.now());
        // register new user 
        userAuthController.registerUser(registerUser);
        when(userAuthController.registerUser(registerUser)).thenReturn(registerUser);
        ResponseEntity<?> response = userAuthController.registerUser(registerUser);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // check if user is registered
        assertEquals("user registered", response.getBody());

        // if the user has already registered
        when(userAuthController.registerUser(registerUser)).thenReturn(null);
        ResponseEntity<?> response = userAuthController.registerUser(registerUser);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());        
    }
    @Test
    public void testLoginUser() throws UserAuthenticationException {
        // register new user 
        User loginUser = new User(1, "test", "test@solosavings.com", "hhhh", LocalDate.now(), 100.00, LocalDate.now());
        userAuthController.registerUser(registerUser);
        when(userAuthController.registerUser(registerUser)).thenReturn(registerUser);
        ResponseEntity<?> response = userAuthController.registerUser(registerUser);
        
        // login user
        userAuthController.loginUser(loginUser);
        when(userAuthController.loginUser(loginUser)).thenReturn(loginUser);
        ResponseEntity<?> response = userAuthController.loginUser(loginUser);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // if user is not registered or password is incorrect
        User loginErrorUser = new User(1, "test", "test@solosavings.com", "hhh", LocalDate.now(), 100.00, LocalDate.now());
        userAuthController.loginUser(loginErrorUser);
        when(userAuthController.loginUser(loginErrorUser)).thenReturn(null);
        ResponseEntity<?> response = userAuthController.loginUser(loginErrorUser);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
