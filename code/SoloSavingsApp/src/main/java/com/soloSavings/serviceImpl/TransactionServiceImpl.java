package com.soloSavings.serviceImpl;

import com.soloSavings.exceptions.TransactionException;
import com.soloSavings.model.Transaction;
import com.soloSavings.model.User;
import com.soloSavings.model.helper.TransactionType;
import com.soloSavings.repository.TransactionRepository;
import com.soloSavings.repository.UserRepository;
import com.soloSavings.service.TransactionService;
import com.soloSavings.utils.Validation;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/*
 * Copyright (c) 2023 Team 2 - SoloSavings
 * Boston University MET CS 673 - Software Engineering
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Team 2 - SoloSavings Application
 */
@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @Override

    public Double addTransaction(Integer user_id, Transaction transaction) throws TransactionException {
        if(transaction.getTransaction_type().equals(TransactionType.CREDIT)){
            return addIncome(user_id,transaction);
        } else {
            return 0.0;
        }
    }

    //Expenses


    //Income


    private Double addIncome(Integer user_id, Transaction transaction) throws TransactionException {
        if(Validation.validateIncome(transaction.getAmount())){
            //given user already authenticated
            User user = userRepository.findById(user_id).get();

            // expected source and amount info already there
            transaction.setUser_id(user_id);
            transaction.setTransaction_date(LocalDate.now());
            transactionRepository.save(transaction);

            user.setBalance_amount(user.getBalance_amount() + transaction.getAmount());
            user = userRepository.save(user);

            return user.getBalance_amount();
        }
        throw new TransactionException("Invalid Income Amount!"); // maybe have this in constant file
    }

}
