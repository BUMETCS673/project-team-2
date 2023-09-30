package com.soloSavings.controller;

import com.soloSavings.exceptions.UserAuthenticationException;
import com.soloSavings.model.Login;
import com.soloSavings.model.User;
import com.soloSavings.service.UserService;
import jakarta.persistence.NonUniqueResultException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@SpringBootTest
public class UserAuthenticationControllerTest {
    @InjectMocks
    UserAuthenticationController userAuthController;

    @Mock
    UserService userService;

    @Test
    public void testRegisterUser() throws UserAuthenticationException {
        User registerUser = new User(1, "test", "test@solosavings.com", "hhhh", LocalDate.now(), 100.00, LocalDate.now());
        // register new user
        when(userService.save(registerUser)).thenReturn(registerUser);
        ResponseEntity<?> response = userAuthController.registerUser(registerUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("user registered", response.getBody());
    }

    @Test
    public void testRegisterUserAlreadyRegistered() throws UserAuthenticationException {
        User registerUser = new User(1, "test", "test@solosavings.com", "hhhh", LocalDate.now(), 100.00, LocalDate.now());
        // register new user
        when(userService.save(registerUser)).thenThrow(NonUniqueResultException.class);
        ResponseEntity<?> response = userAuthController.registerUser(registerUser);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("email " + registerUser.getEmail() + " already registered", response.getBody());
    }
    @Test
    public void testLoginUser() throws UserAuthenticationException {
        // register new user
        User loginUser = new User(1, "test", "test@solosavings.com", "hhhh", LocalDate.now(), 100.00, LocalDate.now());

        when(userService.getPasswordHash(anyString())).thenReturn("hhhh");
        ResponseEntity<?> response = userAuthController.registerUser(loginUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());


    }

    @Test
    public void testLoginUserWrongPassword() throws UserAuthenticationException {
        // if user is not registered or password is incorrect
        Login loginData = new Login("test@solosavings.com", "hhhh");
        when(userService.getPasswordHash(anyString())).thenReturn("wrong");
        ResponseEntity<?> response = userAuthController.loginUser(loginData);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }
}
