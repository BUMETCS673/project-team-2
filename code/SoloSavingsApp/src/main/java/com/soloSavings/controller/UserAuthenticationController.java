package com.soloSavings.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/*
 * Copyright (c) 2023 Team 2 - SoloSavings
 * Boston University MET CS 673 - Software Engineering
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Team 2 - SoloSavings Application
 */
@Controller
public class UserAuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
    @GetMapping("/solosavings/register")
    public String register() {
        logger.info(" Register an account with SoloSavings Application");
        return "register";
    }
    @GetMapping("/solosavings/login")
    public String login() {
        logger.info(" Login to SoloSavings Application");
        return "login";
    }
}
