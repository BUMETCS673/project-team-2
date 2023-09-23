package com.soloSavings.service;

import com.soloSavings.exceptions.TransactionException;
import com.soloSavings.model.Transaction;

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


    //Income
    public Double addTransaction(Integer user_id, Transaction transaction) throws TransactionException;

}
