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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.boot.web.servlet.error.ErrorController;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/")
    public String home() {
        return "redirect:/solosavings";
    }

    @GetMapping("/solosavings")
    public String welcome() {
        logger.info(" Welcome to SoloSavings Application");
        return "home";
    }

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

    @RequestMapping("/error")
    public String handleError() {
        // Handle errors and return the appropriate template
        return "error"; // For error JSP page
    }
}
