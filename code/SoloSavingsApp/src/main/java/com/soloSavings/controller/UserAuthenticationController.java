package com.soloSavings.controller;

import com.soloSavings.model.User;
import com.soloSavings.serviceImpl.UserServiceImp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

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

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUser(@RequestBody User user) {
        logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Request to create a new user: {}", user);
        ResponseEntity.ok("User registered successfully");
        return "redirect:/solosavings";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginUser(@RequestBody User user) {
        logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Request to log in an user: {}", user);
        ResponseEntity.ok("User logged in successfully");
        return "redirect:/solosavings";
    }

    @GetMapping("/dummy-user")
    public void createUser() throws NoSuchAlgorithmException {
        logger.info("!!!!!!!!!!!!!!Request to create a new dummy user!!!!!!!!!!!!!!");
        User user = new User("hello", "hello@hi.com", "hi123", 100.00);
    }
}
