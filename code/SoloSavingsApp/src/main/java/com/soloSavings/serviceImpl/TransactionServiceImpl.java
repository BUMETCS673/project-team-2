package com.soloSavings.serviceImpl;

import com.soloSavings.exceptions.TransactionException;
import com.soloSavings.model.Transaction;
import com.soloSavings.model.User;
import com.soloSavings.model.helper.TransactionType;
import com.soloSavings.repository.TransactionRepository;
import com.soloSavings.repository.UserRepository;
import com.soloSavings.service.TransactionService;
import com.soloSavings.utils.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

import static com.soloSavings.utils.Constants.*;

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

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionServiceImpl(UserRepository userRepository, TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
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
    public Double getThisMonthTotalAmount(Integer userId, TransactionType transactionType) throws TransactionException {
        int thisMonth = YearMonth.now().getMonth().getValue();
        int thisYear = YearMonth.now().getYear();
        return calculateMonthlyAmount(thisMonth,thisYear,userId,transactionType);
    }

    @Override
    public Double addTransaction(Integer user_id, Transaction transaction) throws TransactionException {
        User user = userRepository.findById(user_id).orElseThrow(() -> new TransactionException("User not found!"));
        if(Validation.validateTransaction(user.getBalance_amount(),transaction.getAmount(),transaction.getTransaction_type())){
            transaction.setUser_id(user_id);
            transaction.setTransaction_date(LocalDate.now());
            transactionRepository.save(transaction);

            user.setBalance_amount(getNewUserBalance(user,transaction));
            user.setLast_updated(LocalDate.now());
            user = userRepository.save(user);
            return user.getBalance_amount();
        } else {
            throw new TransactionException(INVALID_TRANSACTION_AMOUNT);
        }
    }

    @Override

    public List<Transaction> getTransactionsByUser(Integer user_id) throws TransactionException {
        User user = userRepository.findById(user_id).orElseThrow(() -> new TransactionException("User not found!"));
        return transactionRepository.findByTransactionUser(user_id);

    }

    public Double deleteTransaction(Integer user_id, Integer transaction_id) throws TransactionException {
        User user = userRepository.findById(user_id).orElseThrow(() -> new TransactionException("User not found!"));
        Transaction transaction = transactionRepository.findById(transaction_id).orElseThrow(() -> new TransactionException("Transaction not found!"));
        if (!Objects.equals(transaction.getUser_id(), user_id)) {
            throw new TransactionException("Invalid Transaction Request");
        } else if (transaction.isDebit() || transaction.isCredit() && getUserBalanceAfterRemoval(user, transaction) >= 0) {
            transactionRepository.deleteById(transaction_id);
           // return updateUserBalance(user, transaction, "remove");
            user.setBalance_amount(getUserBalanceAfterRemoval(user, transaction));
            user.setLast_updated(LocalDate.now());
            user = userRepository.save(user);
            return user.getBalance_amount();
        } else {
            throw new TransactionException("Income transaction required to cover expense. Can not delete this transaction. Please review!");
        }


    }

    private Double getUserBalanceAfterRemoval(User user, Transaction transaction) {
        if (transaction.isCredit()) {
            return user.getBalance_amount() - transaction.getAmount();
        } else {
            return user.getBalance_amount() + transaction.getAmount();
        }
    }

    private Double getNewUserBalance(User user, Transaction transaction) {
        if (transaction.isCredit()) {
            return user.getBalance_amount() + transaction.getAmount();
        } else {
            return user.getBalance_amount() - transaction.getAmount();
        }
    }
    
    public List<Transaction> getTransactionsForUser(Integer userId) {
        return transactionRepository.findAllByUserId(userId);
    }
    
    @Override
    public void exportToCsv(List<Transaction> transactions, String filePath) throws IOException {
	    try (FileWriter writer = new FileWriter(filePath)) {
	        // Write CSV header
	        writer.append("Transaction ID,User ID,Source,Transaction Type,Amount,Transaction Date\n");

	        // Iterate through the list of transactions and write data for each transaction
	        for (Transaction transaction : transactions) {
	            try {
	                writer.append(String.format("%d,%d,%s,%s,%.2f,%s\n",
	                        transaction.getTransaction_id(),
	                        transaction.getUser_id(),
	                        transaction.getSource(),
	                        transaction.getTransaction_type(),
	                        transaction.getAmount(),
	                        transaction.getTransaction_date()
	                ));
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
    }

    @Override
    public List<Map<Object, Object>> getMonthlyAnalyticsByYear(Integer userId, Integer year, TransactionType transactionType) throws TransactionException {
            logger.info(">>>In Transaction Service: getting 12 months analytics");
            // initialize fields
            List<Map<Object, Object>> list = new ArrayList<>();
            Map<Object,Object> map = null;
            double amount = 0.0;

            // populate data into 12 months buckets
            for(int i = 1; i <= 12; i++){
                map = new HashMap<Object,Object>();
                // calculate total amount of either income/expense for specific month
                amount = calculateMonthlyAmount(i,year,userId,transactionType);
                map.put("label", LIST_OF_MONTHS[i-1]);// adding label for each month
                map.put("y",amount);// adding data point for each month
                list.add(map);
            }
            return list;
    }

    @Override
    public Double calculateMonthlyAmount(int month, int year, int userId, TransactionType transType) throws TransactionException {
        try {
            logger.info(">>>In TransactionService, calculating monthly amount");
            List<Transaction> transactions = transactionRepository.findByMonthAndType(month,year,transType,userId);
            return transactions.stream()
                    .mapToDouble(Transaction::getAmount)
                    .sum();
        } catch(Exception e){
            logger.error(String.format(">>>Error in TransactionService, calculateMonthlyAmount : %s",e.getMessage()));
            throw new TransactionException(INTERNAL_SERVER_ERROR);
        }
    }
}
