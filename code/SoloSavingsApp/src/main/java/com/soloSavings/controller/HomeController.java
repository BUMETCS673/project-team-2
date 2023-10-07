package com.soloSavings.controller;
/*
 * Copyright (c) 2023 Team 2 - SoloSavings
 * Boston University MET CS 673 - Software Engineering
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Team 2 - SoloSavings Application
 */
import com.soloSavings.config.SecurityConfig;
import com.soloSavings.exceptions.TransactionException;
import com.soloSavings.model.Transaction;
import com.soloSavings.service.SecurityContext;
import com.soloSavings.service.TransactionService;
import com.soloSavings.serviceImpl.CsvExportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/")
    public String home() {
        return "redirect:/solosavings";
    }

    @GetMapping("/solosavings")
    public String welcome() {
        logger.info("Welcome to SoloSavings Application");
        return "home";
    }

    @GetMapping("/solosavings/register")
    public String register() {
        logger.info("Register an account with SoloSavings Application");
        return "register";
    }

    @GetMapping("/solosavings/login")
    public String login() {
        logger.info("Login to SoloSavings Application");
        return "login";
    }
    
    @GetMapping("/solosavings/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/solosavings/analytics")
    public String analytics() {
        return "analytics";
    }

    @GetMapping("/solosavings/transactionHistory")
    public String transactionhistory() {
            return "transactionHistory";
    }
}
