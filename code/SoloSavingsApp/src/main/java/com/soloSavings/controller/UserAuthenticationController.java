package com.soloSavings.controller;

import com.soloSavings.config.JwtUtil;
import com.soloSavings.config.SecurityConfig;
import com.soloSavings.model.JwtResponse;
import com.soloSavings.model.Login;
import com.soloSavings.model.User;
//import com.soloSavings.model.Token;
//import com.soloSavings.service.TokenManagerService;
import com.soloSavings.service.UserService;
import jakarta.persistence.NonUniqueResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    /*
    @Autowired
    private TokenManagerService tokenManagerService;
*/
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
        logger.info("Request to log in as the user: {}", loginData.email());
        String db_password_hash = userService.getPasswordHash(loginData.email());
        if(SecurityConfig.checkPassword(db_password_hash, loginData.password())) {
            logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!PASS PASSWORD CHECKING!!!!!!!!!!!!!!!!!!");
            User user = userService.getUserByEmail(loginData.email());
            logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!: {}", user.getUsername());
            final String token = JwtUtil.generateToken();
            JwtResponse jwtResponse = new JwtResponse(token);

            return ResponseEntity.ok().body(jwtResponse);
        } else {
            return new ResponseEntity<>("Something goes wrong, log in is not successful, please try again.", HttpStatus.UNAUTHORIZED);
        }
    }
}
