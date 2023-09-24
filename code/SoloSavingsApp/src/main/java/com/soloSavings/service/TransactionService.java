package com.soloSavings.service;

import com.soloSavings.exceptions.TransactionException;
import com.soloSavings.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Copyright (c) 2023 Team 2 - SoloSavings
 * Boston University MET CS 673 - Software Engineering
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Team 2 - SoloSavings Application
 */

@Service
public interface TransactionService {


    //Expenses
    public List<Transaction> getTransactionsByType(Integer user_id, String transaction_type) throws TransactionException ;

    //Income
    public Double addTransaction(Integer user_id, Transaction transaction) throws TransactionException;

    Double getThisMonthExpense(Integer userId) throws TransactionException;

    Double getThisMonthIncome(Integer userId) throws TransactionException;
}
