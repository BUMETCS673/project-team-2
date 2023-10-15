package com.soloSavings.controller;

import com.soloSavings.config.JwtUtil;
import com.soloSavings.exceptions.UserAuthenticationException;
import com.soloSavings.model.Login;
import com.soloSavings.model.TokenDetails;
import com.soloSavings.model.User;
import com.soloSavings.service.UserService;
import jakarta.persistence.NonUniqueResultException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static com.soloSavings.utils.Constants.INVALID_USERNAME_OR_PASSWORD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@SpringBootTest
public class UserAuthenticationControllerTest {
    @InjectMocks
    UserAuthenticationController userAuthController;
    @Mock
    UserService userService;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    AuthenticationManager authenticationManager;

    @Test
    public void testRegisterUser() throws UserAuthenticationException {
        User registerUser = new User(1, "test", "test@solosavings.com", "hhhh", LocalDate.now(), 100.00, LocalDate.now());
        // register new user
        doNothing().when(userService).save(any());
        ResponseEntity<?> response = userAuthController.registerUser(registerUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("The user account with email has successfully created", response.getBody());

        verify(userService).save(registerUser);
    }

    @Test
    public void testRegisterUserAlreadyRegistered() {
        User registerUser = new User(1, "test", "test@solosavings.com", "hhhh", LocalDate.now(), 100.00, LocalDate.now());
        // register new user
        doThrow(new NonUniqueResultException()).when(userService).save(any());
        ResponseEntity<?> response = userAuthController.registerUser(registerUser);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("The email has already registered.", response.getBody());
    }

    @Test
    public void testLoginUser(){
        Login loginData = new Login("test@solosavings.com", "hhhh");
        Set<GrantedAuthority> test = new HashSet<>();
        UserDetails userDetails =  new org.springframework.security.core.userdetails.User("test","test",test);
        TokenDetails token = jwtUtil.generateToken("testing-token");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userService.loadUserByUsername(loginData.username())).thenReturn(userDetails);
        when(jwtUtil.generateToken(anyString())).thenReturn(token);
        ResponseEntity<?> response = userAuthController.loginUser(loginData);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(token, response.getBody());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userService).loadUserByUsername(loginData.username());
    }

    @Test
    public void testLoginUserInvalid() throws UserAuthenticationException {
        Login loginData = new Login("test@solosavings.com", "hhhh");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException(""));
        ResponseEntity<?> response = userAuthController.loginUser(loginData);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(INVALID_USERNAME_OR_PASSWORD, response.getBody());
    }
}
