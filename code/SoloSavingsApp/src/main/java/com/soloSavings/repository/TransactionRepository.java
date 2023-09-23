package com.soloSavings.repository;

import com.soloSavings.model.Transaction;
import com.soloSavings.model.helper.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * Copyright (c) 2023 Team 2 - SoloSavings
 * Boston University MET CS 673 - Software Engineering
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Team 2 - SoloSavings Application
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    //Expenses


    //Income
    //List<Transaction> findByTransactionType(TransactionType transactionType);

}
