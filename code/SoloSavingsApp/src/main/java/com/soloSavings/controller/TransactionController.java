package com.soloSavings.controller;

import com.soloSavings.exceptions.TransactionException;
import com.soloSavings.model.Transaction;
import com.soloSavings.service.TransactionService;
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
@RequestMapping("transaction")
public class TransactionController {

    //Expenses


    //Income
    @Autowired
    TransactionService transactionServiceImpl;

    @RequestMapping(value = "/add/{id}", method = RequestMethod.POST)
    public ResponseEntity<Double> addIncome (@PathVariable("id") Integer id, @RequestBody Transaction transaction){
        try{
            Double newBalance = transactionServiceImpl.addTransaction(id,transaction);
            return new ResponseEntity<>(newBalance, HttpStatus.OK);

       }
        catch (TransactionException e){
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY); // 422 code for invalid requestbody for transaction
        }
    }
}
