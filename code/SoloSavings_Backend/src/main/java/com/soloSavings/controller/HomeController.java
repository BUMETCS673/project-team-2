package com.soloSavings.controller;
/*
 * Copyright (c) 2023 Team 2 - SoloSavings
 * Boston University MET CS 673 - Software Engineering
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Team 2 - SoloSavings Application
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/")
    public String welcome() {
        // Log a message using the logger
        logger.info(" Welcome to SoloSavings Application");

        // Return the name of your JSP or HTML file
        return "welcome"; // This should return the name of your view file  ,this will replace with React
    }
}
