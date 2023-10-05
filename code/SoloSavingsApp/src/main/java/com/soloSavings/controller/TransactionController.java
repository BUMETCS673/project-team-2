package com.soloSavings.controller;

import com.soloSavings.Application;
import com.soloSavings.exceptions.TransactionException;
import com.soloSavings.model.Transaction;
import com.soloSavings.model.helper.TransactionType;
import com.soloSavings.service.TransactionService;
import com.soloSavings.serviceImpl.CsvExportService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
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
@RequestMapping("transaction")
public class TransactionController {

    //Expenses
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    //Income
    @Autowired
    TransactionService transactionServiceImpl;
    
    @Autowired
     CsvExportService csvExportService;


    @RequestMapping(value = "/add/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> addTransaction (@PathVariable("id") Integer id, @RequestBody Transaction transaction){
        try{
            Double newBalance = transactionServiceImpl.addTransaction(id,transaction);
            return new ResponseEntity<>(newBalance, HttpStatus.OK);
       }
        catch (TransactionException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY); // 422 code for invalid requestbody for transaction
        }
    }

    @GetMapping("/{user_id}/{transaction_type}")
    public ResponseEntity<?> getTransactionsByType(@PathVariable("user_id") Integer user_id, @PathVariable("transaction_type") String transaction_type) {
        try {
            List<Transaction> transactionsList = transactionServiceImpl.getTransactionsByType(user_id, transaction_type);
            return new ResponseEntity<>(transactionsList, HttpStatus.OK);
        } catch (TransactionException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY); // 422 code for invalid requestbody for transaction
        }
    }

    @GetMapping("monthly/expense/{user_id}")
    public ResponseEntity<?> getThisMonthExpense(@PathVariable("user_id") Integer user_id) {
        try {
            Double thisMonthExpense = transactionServiceImpl.getThisMonthExpense(user_id);
            return new ResponseEntity<>(thisMonthExpense, HttpStatus.OK);
        } catch (TransactionException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("monthly/income/{user_id}")
    public ResponseEntity<?> getThisMonthIncome(@PathVariable("user_id") Integer user_id) {
        try {
            Double thisMonthIncome = transactionServiceImpl.getThisMonthIncome(user_id);
            return new ResponseEntity<>(thisMonthIncome, HttpStatus.OK);
        } catch (TransactionException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NO_CONTENT);
        }
        
    }
    @GetMapping("/{user_id}/export/csv")
    public ResponseEntity<Resource> exportTransactionsToCsv(@PathVariable("user_id") Integer userId) throws IOException {
        Optional<Transaction> transactions = transactionServiceImpl.getTransactionsForUser(userId);

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

 
}

    

