package com.soloSavings.controller;

import com.soloSavings.config.SecurityConfig;
import com.soloSavings.model.Login;
//import com.soloSavings.model.Token;
import com.soloSavings.model.User;
//import com.soloSavings.service.TokenManagerService;
import com.soloSavings.service.UserService;
import jakarta.persistence.NonUniqueResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            User result = userService.save(user);
            return ResponseEntity.ok().body("user registered");
        } catch (NonUniqueResultException e) {
            logger.info(e.getMessage());
            return new ResponseEntity<>("email " + user.getEmail() + " already registered", HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity loginUser(@RequestBody Login loginData) {
        logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Request to log in an user: {}", loginData.email());
        // REMOVE ME
        logger.info("And password: {}", loginData.password());
        String db_password_hash = userService.getPasswordHash(loginData.email());
        if(SecurityConfig.checkPassword(db_password_hash, loginData.password())) {
            //Token token = new Token(loginData.email());
            //tokenManagerService.add_token(token);
            //return new ResponseEntity<>(token.getUuid().toString(), HttpStatus.OK);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity<>("", HttpStatus.UNAUTHORIZED);
        }
    }
}
