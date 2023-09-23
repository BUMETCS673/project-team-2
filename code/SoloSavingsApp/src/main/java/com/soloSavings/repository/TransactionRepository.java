package com.soloSavings.repository;

import com.soloSavings.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Copyright (c) 2023 Team 2 - SoloSavings
 * Boston University MET CS 673 - Software Engineering
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Team 2 - SoloSavings Application
 */
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    //Expenses


    //Income


}
