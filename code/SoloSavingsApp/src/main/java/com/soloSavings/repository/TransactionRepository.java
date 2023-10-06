package com.soloSavings.repository;

import com.soloSavings.model.Transaction;
import com.soloSavings.model.helper.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
    @Query("SELECT t FROM Transaction t WHERE t.user_id = ?1 and t.transaction_type = ?2")
    List<Transaction> findByTransactionType(Integer user_id, TransactionType transaction_type);

    @Query("SELECT e FROM Transaction e WHERE MONTH(e.transaction_date) = MONTH(CURRENT_DATE()) AND YEAR(e.transaction_date) = YEAR(CURRENT_DATE()) AND  e.transaction_type = ?1")
    List<Transaction> findByCurrentMonth(TransactionType trans_type);

    @Query("SELECT e FROM Transaction e WHERE  e.user_id = ?1")
    List<Transaction> findAllByUserId(Integer userId);

    @Query("SELECT e FROM Transaction e WHERE MONTH(e.transaction_date) = ?1 AND YEAR(e.transaction_date) = ?2 AND e.transaction_type = ?3")
    List<Transaction> findByMonthAndType(int month, int year, TransactionType transaction_type);

}
