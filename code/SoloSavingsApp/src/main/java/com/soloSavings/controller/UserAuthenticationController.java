package com.soloSavings.controller;

import com.soloSavings.config.JwtFilter;
import com.soloSavings.config.JwtUtil;
import com.soloSavings.config.SecurityConfig;
import com.soloSavings.model.JwtResponse;
import com.soloSavings.model.Login;
import com.soloSavings.model.User;
//import com.soloSavings.model.Token;
//import com.soloSavings.service.TokenManagerService;
import com.soloSavings.service.EmailService;
import com.soloSavings.service.UserService;
import jakarta.persistence.NonUniqueResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

/*
 * Copyright (c) 2023 Team 2 - SoloSavings
 * Boston University MET CS 673 - Software Engineering
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Team 2 - SoloSavings Application
 */
@RestController
@RequestMapping("/api")
public class UserAuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(UserAuthenticationController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity registerUser(@RequestBody User user) {
        logger.info("Request to create a new user: {}", user);
        try {
            userService.save(user);
            return ResponseEntity.ok().body("The user account with email " + user.getEmail() + " has successfully created");
        } catch (NonUniqueResultException e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>("The email " + user.getEmail() + " already registered.", HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity loginUser(@RequestBody Login loginData) {
        logger.info("Request to log in as the user: {}", loginData.username());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginData.username(), loginData.password()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

        UserDetails userDetails = userService.loadUserByUsername(loginData.username());
        String token = jwtUtil.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(token);
    }

    @RequestMapping(value = "/forget-password", method = RequestMethod.POST)
    public ResponseEntity forgetPassword(@RequestBody Login loginData) {
        logger.info("Request to send forget password link as the user: {}", loginData.username());
        try {
            UserDetails userDetails = userService.loadUserByUsername(loginData.username());
            User userInfo = userService.getUserByName(userDetails.getUsername());
            String resetToken = UUID.randomUUID().toString();
            System.out.println("!!!!!!!!!!!!!!!!!!!!" + userInfo.getEmail() + resetToken);
            return ResponseEntity.ok("User found, email sent");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not found");
        }
    }

    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    public String resetPassword(@RequestParam("token") String token, @RequestParam("password") String newPassword) {
        // Check if the token is valid
//        User user = userService.findByResetToken(token);
//        if (user == null || user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
//            // Token is invalid or expired, handle accordingly
//        }
//
//        // Update the user's password
//        userService.updatePassword(user, newPassword);
//
//        // Send a confirmation email
//        emailService.sendPasswordResetEmail(user.getEmail(), token);
//
//        // Invalidate the token
//        user.setResetToken(null);
//        user.setResetTokenExpiry(null);
//        userService.save(user);
//
//        // Redirect to a login page or a success page
        return "redirect:/login";
    }
}
