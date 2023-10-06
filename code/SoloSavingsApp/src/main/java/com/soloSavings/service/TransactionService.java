package com.soloSavings.service;

import com.soloSavings.exceptions.TransactionException;
import com.soloSavings.model.Transaction;
import com.soloSavings.model.helper.TransactionType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Map;

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
    List<Transaction> getTransactionsByType(Integer user_id, String transaction_type) throws TransactionException ;

    Double addTransaction(Integer user_id, Transaction transaction) throws TransactionException;

    Double getThisMonthExpense(Integer userId) throws TransactionException;

    Double getThisMonthIncome(Integer userId) throws TransactionException;
    public Optional<Transaction> getTransactionsForUser(Integer userId) ;
    

    List<Map<Object, Object>> getMonthlyAnalyticsByYear(Integer userId, Integer year, TransactionType transactionType) throws TransactionException;
}
