package com.soloSavings.serviceImpl;

import com.soloSavings.exceptions.TransactionException;
import com.soloSavings.model.Transaction;
import com.soloSavings.model.User;
import com.soloSavings.model.helper.TransactionType;
import com.soloSavings.repository.TransactionRepository;
import com.soloSavings.repository.UserRepository;
import com.soloSavings.service.TransactionService;
import com.soloSavings.utils.Constants;
import com.soloSavings.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
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
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    TransactionRepository transactionRepository;

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

    @Override
    public Double addTransaction(Integer user_id, Transaction transaction) throws TransactionException {
        User user = userRepository.findById(user_id).orElseThrow(() -> new TransactionException("User not found"));
        if(Validation.validateTransaction(user.getBalance_amount(),transaction.getAmount(),transaction.getTransaction_type())){
            transaction.setUser_id(user_id);
            transaction.setTransaction_date(LocalDate.now());
            transactionRepository.save(transaction);

            user.setBalance_amount(getNewUserBalance(user,transaction));
            user.setLast_updated(LocalDate.now());
            user = userRepository.save(user);
            return user.getBalance_amount();
        } else {
            throw new TransactionException("Invalid transaction amount, Please input correct transaction amount!");
        }
    }
    private Double getNewUserBalance(User user, Transaction transaction){
        if(transaction.getTransaction_type().equals(TransactionType.CREDIT)){
            return user.getBalance_amount() + transaction.getAmount();
        } else {
            return user.getBalance_amount() - transaction.getAmount();
        }
    }

    @Override
    public List<Map<Object, Object>> getMonthlyAnalyticsByYear(Integer userId, Integer year, TransactionType transactionType) throws TransactionException {
        try{
            List<Map<Object, Object>> list = new ArrayList<>();
            Map<Object,Object> map = null;
            double income = 0.0;
            // populate data into 12 months buckets
            for(int i = 1; i <= 12; i++){
                map = new HashMap<Object,Object>();
                List<Transaction> transactions = transactionRepository.findByMonthAndType(i,year,transactionType);
                income = transactions.stream()
                        .mapToDouble(Transaction::getAmount)
                        .sum();
                map.put("label", Constants.listOfMonth[i-1]);
                map.put("y",income);
                list.add(map);
            }
            return list;
        } catch (Exception e){
            throw new TransactionException("Internal Service Error, Please try again later!");
        }
    }
}
