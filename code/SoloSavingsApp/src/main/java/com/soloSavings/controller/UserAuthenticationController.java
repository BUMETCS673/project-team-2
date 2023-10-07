package com.soloSavings.controller;

import com.soloSavings.config.JwtUtil;
import com.soloSavings.model.Login;
import com.soloSavings.model.ResetPassword;
import com.soloSavings.model.User;
import com.soloSavings.service.UserService;
import com.soloSavings.serviceImpl.PasswordResetService;
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
import org.springframework.web.bind.annotation.*;

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
    private PasswordResetService passwordResetService;
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
        try {
            UserDetails userDetails = userService.loadUserByUsername(loginData.username());
            try {
                User userInfo = userService.getUserByName(userDetails.getUsername());
                logger.info("Request to send forget password link as the user: {}", loginData.username());
                passwordResetService.initiatePasswordReset(userInfo.getUsername(), userInfo.getEmail());
            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Issue with sending email.");
            }
            return ResponseEntity.ok("User found, email sent");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong.");
        }
    }

    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    public ResponseEntity resetPassword(@RequestBody ResetPassword resetPasswordData) {
        logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        try {
            String userName = passwordResetService.retrieveUserName(resetPasswordData.token());
            logger.info("RESETTING PASSWORD FOR: ", userName);
//            if (user == null || user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
//                // Token is invalid or expired, handle accordingly
//            }

//            // Update the user's password
//            userService.updatePassword(user, newPassword);
//
//            // Invalidate the token
//            user.setResetToken(null);
//            user.setResetTokenExpiry(null);
//            userService.save(user);
            return ResponseEntity.ok("Password reset successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong.");
        }
    }
}
