package com.soloSavings.service;

import com.soloSavings.exceptions.TransactionException;
import com.soloSavings.model.Transaction;
import com.soloSavings.model.helper.TransactionType;

import java.util.List;

/*
 * Copyright (c) 2023 Team 2 - SoloSavings
 * Boston University MET CS 673 - Software Engineering
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Team 2 - SoloSavings Application
 */
public interface TransactionService {


    //Expenses
    public List<Transaction> getTransactionsByType(Integer user_id, TransactionType transaction_type) throws TransactionException ;

    //Income
    public Double addTransaction(Integer user_id, Transaction transaction) throws TransactionException;

}
