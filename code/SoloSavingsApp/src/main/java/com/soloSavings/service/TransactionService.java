package com.soloSavings.service;

import com.soloSavings.exceptions.TransactionException;
import com.soloSavings.model.Transaction;
import com.soloSavings.model.helper.TransactionType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
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
    List<Transaction> getTransactionsByUser(Integer user_id) throws TransactionException ;
    Double addTransaction(Integer user_id, Transaction transaction) throws TransactionException;
    List<Transaction> getTransactionsForUser(Integer userId) ;
    void exportToCsv(List<Transaction> transactions, String filePath) throws IOException ;
    Double deleteTransaction(Integer user_id, Integer transaction_id) throws TransactionException;
    List<Map<Object, Object>> getMonthlyAnalyticsByYear(Integer userId, Integer year, TransactionType transactionType) throws TransactionException;
    Double calculateMonthlyAmount(int month, int year, int userId, TransactionType transType) throws TransactionException;
    Double getThisMonthTotalAmount(Integer userId, TransactionType transactionType) throws TransactionException;
}
