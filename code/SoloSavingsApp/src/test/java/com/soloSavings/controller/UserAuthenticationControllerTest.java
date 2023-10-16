package com.soloSavings.controller;

import com.soloSavings.config.JwtUtil;
import com.soloSavings.model.*;
import com.soloSavings.service.UserService;
import com.soloSavings.serviceImpl.PasswordResetService;
import jakarta.persistence.NonUniqueResultException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    @Mock
    private PasswordResetService passwordResetService;
    User user;
    @BeforeEach
    void setup() {
        user = User.builder().user_id(1)
                .username("test")
                .email("test@gmail.com")
                .password_hash("passwordHash")
                .registration_date(null)
                .balance_amount(10.00)
                .last_updated(null)
                .build();
    }
    @Test
    public void testRegisterUser(){
        //User registerUser = new User(1, "test", "test@solosavings.com", "hhhh", LocalDate.now(), 100.00, LocalDate.now());
        // register new user
        doNothing().when(userService).save(any());
        ResponseEntity<?> response = userAuthController.registerUser(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("The user account with email has successfully created", response.getBody());

        verify(userService).save(user);
    }

    @Test
    public void testRegisterUserAlreadyRegistered() {
        //User registerUser = new User(1, "test", "test@solosavings.com", "hhhh", LocalDate.now(), 100.00, LocalDate.now());
        // register new user
        doThrow(new NonUniqueResultException()).when(userService).save(any());
        ResponseEntity<?> response = userAuthController.registerUser(user);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("The email has already registered.", response.getBody());
    }

    @Test
    public void testLoginUser(){
        Login loginData = new Login("test@solosavings.com", "hhhh");
        Set<GrantedAuthority> auth = new HashSet<>();
        UserDetails userDetails =  new org.springframework.security.core.userdetails.User("test","test",auth);
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
    public void testLoginUserInvalid() {
        Login loginData = new Login("test@solosavings.com", "hhhh");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException(""));
        ResponseEntity<?> response = userAuthController.loginUser(loginData);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(INVALID_USERNAME_OR_PASSWORD, response.getBody());
    }

    @Test
    public void testForgetPassword() {
        Login loginData = new Login("test@solosavings.com", "hhhh");
        Set<GrantedAuthority> auth = new HashSet<>();
        UserDetails userDetails =  new org.springframework.security.core.userdetails.User("test","test",auth);

        when(userService.loadUserByUsername(loginData.username())).thenReturn(userDetails);
        when(userService.getUserByName(anyString())).thenReturn(user);
        doNothing().when(passwordResetService).initiatePasswordReset(anyString(),anyString());

        ResponseEntity res = userAuthController.forgetPassword(loginData);

        assertEquals(HttpStatus.OK,res.getStatusCode());
        assertEquals("User found, email sent",res.getBody());
    }
    @Test
    public void testForgetPasswordBadCredentials() {
        Login loginData = new Login("test@solosavings.com", "hhhh");
        Set<GrantedAuthority> auth = new HashSet<>();
        UserDetails userDetails =  new org.springframework.security.core.userdetails.User("test","test",auth);

        when(userService.loadUserByUsername(loginData.username())).thenThrow(BadCredentialsException.class);

        ResponseEntity res = userAuthController.forgetPassword(loginData);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,res.getStatusCode());
        assertEquals("Something went wrong.",res.getBody());
    }

    @Test
    public void testForgetPasswordGeneralException() {
        Login loginData = new Login("test@solosavings.com", "hhhh");
        Set<GrantedAuthority> auth = new HashSet<>();
        UserDetails userDetails =  new org.springframework.security.core.userdetails.User("test","test",auth);

        when(userService.loadUserByUsername(loginData.username())).thenReturn(userDetails);
        when(userService.getUserByName(anyString())).thenThrow(RuntimeException.class);

        ResponseEntity res = userAuthController.forgetPassword(loginData);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,res.getStatusCode());
        assertEquals("Issue with sending email.",res.getBody());
    }

    @Test
    public void testResetPassword() {
        ResetPassword resetPassword = new ResetPassword("token",user.getUsername(),user.getPassword_hash());

        when(passwordResetService.retrieveUserName(anyString())).thenReturn(user.getUsername());
        doNothing().when(userService).setUserNewPassword(anyString(),anyString());
        doNothing().when(passwordResetService).deleteTokenStorageRecord(anyString());

        ResponseEntity res = userAuthController.resetPassword(resetPassword);

        assertEquals(HttpStatus.OK,res.getStatusCode());
        assertEquals("Password reset successfully",res.getBody());
    }

    @Test
    public void testResetPasswordNotAuth() {
        ResetPassword resetPassword = new ResetPassword("token","differentusername",user.getPassword_hash());

        when(passwordResetService.retrieveUserName(anyString())).thenReturn(user.getUsername());

        ResponseEntity res = userAuthController.resetPassword(resetPassword);

        assertEquals(HttpStatus.UNAUTHORIZED,res.getStatusCode());
        assertEquals("Something went wrong.",res.getBody());
    }

    @Test
    public void testResetPasswordGeneralException() {
        ResetPassword resetPassword = new ResetPassword("token","differentusername",user.getPassword_hash());

        when(passwordResetService.retrieveUserName(anyString())).thenThrow(RuntimeException.class);

        ResponseEntity res = userAuthController.resetPassword(resetPassword);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,res.getStatusCode());
        assertEquals("Something went wrong.",res.getBody());
    }
}
