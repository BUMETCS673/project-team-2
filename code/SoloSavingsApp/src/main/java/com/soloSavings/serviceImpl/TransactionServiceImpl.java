package com.soloSavings.serviceImpl;

import com.soloSavings.exceptions.TransactionException;
import com.soloSavings.model.Transaction;
import com.soloSavings.model.User;
import com.soloSavings.model.helper.TransactionType;
import com.soloSavings.repository.TransactionRepository;
import com.soloSavings.repository.UserRepository;
import com.soloSavings.service.TransactionService;
import com.soloSavings.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public Double addTransaction(Integer user_id, Transaction transaction) throws TransactionException {
        if(transaction.getTransaction_type().equals(TransactionType.CREDIT)){
            return addIncome(user_id,transaction);
        }
        if(transaction.getTransaction_type().equals(TransactionType.DEBIT)){
            return addExpense(user_id, transaction);
        }
        throw new TransactionException("Transaction Type Invalid; DEBIT or CREDIT");
    }

    @Override
    public List<Transaction> getTransactionsByType(Integer user_id, String transaction_type) throws TransactionException {
        if(transaction_type.equalsIgnoreCase("CREDIT")){
            return transactionRepository.findByTransactionType(user_id, TransactionType.CREDIT);
        } else if(transaction_type.equalsIgnoreCase("DEBIT")){
            return transactionRepository.findByTransactionType(user_id, TransactionType.DEBIT);
        }
        throw new TransactionException("Invalid URL");
    }

    @Override
    public Double getThisMonthExpense(Integer userId) {
        List<Transaction> transactions = transactionRepository.findByCurrentMonth(TransactionType.DEBIT);
        return transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    @Override
    public Double getThisMonthIncome(Integer userId) {
        List<Transaction> transactions = transactionRepository.findByCurrentMonth(TransactionType.CREDIT);
        return transactions.stream()
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    private Double addExpense(Integer user_id, Transaction transaction) throws TransactionException {
        if(Validation.validateExpense(transaction.getAmount())){
            //given user already authenticated
            User user = userRepository.findById(user_id).get();

            //verify user balance sufficient for expense
            if (transaction.getAmount() <= user.getBalance_amount() ) {
                // expected source and amount info already there
                transaction.setUser_id(user_id);
                transaction.setTransaction_date(LocalDate.now());
                transactionRepository.save(transaction);

                user.setBalance_amount(user.getBalance_amount() - transaction.getAmount());
                user.setLast_updated(LocalDate.now());
                user = userRepository.save(user);

                return user.getBalance_amount();
            }
            throw new TransactionException(("Insufficient Account Balance for Expense Amount!"));

        }
        throw new TransactionException("Invalid Expense Amount!"); // maybe have this in constant file
    }

    private Double addIncome(Integer user_id, Transaction transaction) throws TransactionException {
        if(Validation.validateIncome(transaction.getAmount())){
            //given user already authenticated
            User user = userRepository.findById(user_id).get();

            // expected source and amount info already there
            transaction.setUser_id(user_id);
            transaction.setTransaction_date(LocalDate.now());
            transactionRepository.save(transaction);

            user.setBalance_amount(user.getBalance_amount() + transaction.getAmount());
            user.setLast_updated(LocalDate.now());
            user = userRepository.save(user);

            return user.getBalance_amount();
        }
        throw new TransactionException("Invalid Income Amount!"); // maybe have this in constant file
    }

}
