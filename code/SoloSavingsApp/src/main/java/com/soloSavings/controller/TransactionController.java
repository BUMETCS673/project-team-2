package com.soloSavings.controller;

import com.soloSavings.Application;
import com.soloSavings.exceptions.TransactionException;
import com.soloSavings.model.Transaction;
import com.soloSavings.model.helper.TransactionType;
import com.soloSavings.service.SecurityContext;
import com.soloSavings.service.TransactionService;
import com.soloSavings.serviceImpl.CsvExportService;


import com.soloSavings.serviceImpl.TransactionServiceImpl;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



/*
 * Copyright (c) 2023 Team 2 - SoloSavings
 * Boston University MET CS 673 - Software Engineering
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Team 2 - SoloSavings Application
 */

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @Autowired
    TransactionService transactionServiceImpl;
    
    @Autowired
     CsvExportService csvExportService;

    @Autowired
    SecurityContext securityContext;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> addTransaction (@RequestBody Transaction transaction){
        securityContext.setContext(SecurityContextHolder.getContext());
        try{
            Double newBalance = transactionServiceImpl.addTransaction(securityContext.getCurrentUser().getUser_id(), transaction);
            securityContext.dispose();
            return new ResponseEntity<>(newBalance, HttpStatus.OK);
       }
        catch (TransactionException e){
            securityContext.dispose();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY); // 422 code for invalid requestbody for transaction
        }
    }

    @GetMapping("{transaction_type}")
    public ResponseEntity<?> getTransactionsByType(@PathVariable("transaction_type") String transaction_type) {
        securityContext.setContext(SecurityContextHolder.getContext());
        try {
            List<Transaction> transactionsList = transactionServiceImpl.getTransactionsByType(securityContext.getCurrentUser().getUser_id(), transaction_type);
            securityContext.dispose();
            return new ResponseEntity<>(transactionsList, HttpStatus.OK);
        } catch (TransactionException e) {
            securityContext.dispose();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY); // 422 code for invalid requestbody for transaction
        }
    }

    @GetMapping("monthly/expense")
    public ResponseEntity<?> getThisMonthExpense() {
        securityContext.setContext(SecurityContextHolder.getContext());
        try {
            Double thisMonthExpense = transactionServiceImpl.getThisMonthExpense(securityContext.getCurrentUser().getUser_id());
            securityContext.dispose();
            return new ResponseEntity<>(thisMonthExpense, HttpStatus.OK);
        } catch (TransactionException e) {
            securityContext.dispose();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("monthly/income")
    public ResponseEntity<?> getThisMonthIncome() {
        securityContext.setContext(SecurityContextHolder.getContext());
        try {
            Double thisMonthIncome = transactionServiceImpl.getThisMonthIncome(securityContext.getCurrentUser().getUser_id());
            securityContext.dispose();
            return new ResponseEntity<>(thisMonthIncome, HttpStatus.OK);
        } catch (TransactionException e) {
            securityContext.dispose();
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NO_CONTENT);
        }
        
    }
    @GetMapping("/export/csv")
    public ResponseEntity<?> exportTransactionsToCsv() throws IOException {
        securityContext.setContext(SecurityContextHolder.getContext());
        Integer user_id = securityContext.getCurrentUser().getUser_id();
            Optional<Transaction> transactions = transactionServiceImpl.getTransactionsForUser(user_id);

            // Generate CSV file
            String csvFilePath = "transaction_history.csv";
            csvExportService.exportToCsv(transactions, csvFilePath);

            // Read the content of the CSV file
            byte[] csvFileContent = Files.readAllBytes(Paths.get(csvFilePath));

            // Create a Resource object for the CSV file
            ByteArrayResource resource = new ByteArrayResource(csvFileContent);

            // Set content disposition to trigger a download
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transaction_history.csv");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(csvFileContent.length)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

    }

 
    @GetMapping("analytics/monthly/{type}/{year}")
    public ResponseEntity<?> getMonthlyAnalyticsByYear(
            @PathVariable("type") TransactionType transactionType,
            @PathVariable("year") Integer year) {
        securityContext.setContext(SecurityContextHolder.getContext());
        try {
            List<Map<Object, Object>> incomes = transactionServiceImpl
                    .getMonthlyAnalyticsByYear(securityContext.getCurrentUser().getUser_id(),
                            year,
                            transactionType);
            return new ResponseEntity<>(incomes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("delete/{transaction_id}")
    public ResponseEntity<String> deleteTransaction(@PathVariable("transaction_id") Integer transaction_id){
        logger.info("****Attempting to delete transaction_id " + transaction_id);
        securityContext.setContext(SecurityContextHolder.getContext());
        try {
            Integer user_id = securityContext.getCurrentUser().getUser_id();
            Double userBal = transactionServiceImpl.deleteTransaction(user_id, transaction_id);
            return new ResponseEntity<>("Transaction Successfully Deleted. User Balance is " + userBal , HttpStatus.OK);
        } catch (TransactionException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("history")
    public ResponseEntity<?> getTransactionsHistory() {
       securityContext.setContext(SecurityContextHolder.getContext());
        try {
            Integer user_id = securityContext.getCurrentUser().getUser_id();
            logger.info("Getting Transaction History for " + user_id);

            List<Transaction> transactionsHistory = transactionServiceImpl.getTransactionsByUser(user_id);
            return new ResponseEntity<>(transactionsHistory, HttpStatus.OK);
        } catch (TransactionException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

}

    

