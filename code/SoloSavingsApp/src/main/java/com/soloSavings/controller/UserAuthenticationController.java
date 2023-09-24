package com.soloSavings.controller;

import com.soloSavings.model.User;
import com.soloSavings.repository.UserRepository;
import com.soloSavings.service.UserService;
import com.soloSavings.serviceImpl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        logger.info("Request to create a new user: {}", user);
        User result = userService.save(user);
        return ResponseEntity.ok().body(result);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginUser(@RequestBody User user) {
        logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!Request to log in an user: {}", user);
        ResponseEntity.ok("User logged in successfully");
        return "redirect:/solosavings";
    }
}
