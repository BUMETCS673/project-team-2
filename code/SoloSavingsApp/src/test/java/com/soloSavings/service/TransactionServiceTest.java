package com.soloSavings.unit.service;

import com.soloSavings.exceptions.TransactionException;
import com.soloSavings.model.Transaction;
import com.soloSavings.model.User;
import com.soloSavings.model.helper.TransactionType;
import com.soloSavings.repository.TransactionRepository;
import com.soloSavings.repository.UserRepository;
import com.soloSavings.serviceImpl.TransactionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class TransactionServiceTest {
    @Mock
    UserRepository userRepository;
    @Mock
    TransactionRepository transactionRepository;
    @InjectMocks
    TransactionServiceImpl transactionService;
    @Test
    public void testAddIncome() throws TransactionException {
        //Given
        Double givenAmount = 100.00;
        Double expectedAmount = 200.55;
        User user = new User(1,"","","",null,100.55,null);
        Transaction trans = new Transaction(1,1,null, TransactionType.CREDIT,givenAmount,null);

        //When
        when(userRepository.findById(user.getUser_id())).thenReturn(Optional.of(user));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(trans);
        when(userRepository.save(any(User.class))).thenReturn(user);
        Double actualAmount = transactionService.addTransaction(user.getUser_id(),trans);

        //Then
        Assertions.assertEquals(expectedAmount,actualAmount);
    }
    @Test
    public void testAddIncomeError() throws TransactionException {
        //Given
        Double givenAmount = -1.00;
        User user = new User(1,"","","",null,100.55,null);
        Transaction trans = new Transaction(1,1,null, TransactionType.CREDIT,givenAmount,null);

        //Then
        Assertions.assertThrows(TransactionException.class, () -> {
            transactionService.addTransaction(user.getUser_id(),trans);
        });
    }

    @Test
    public void testGetThisMonthExpense(){
        //Given
        Double expectedAmount = 200.55;
        List<Transaction> transactions = new ArrayList<>();
        Transaction trans = new Transaction(null,null,null, null,100.00,null);
        Transaction trans2 = new Transaction(null,null,null, null,100.55,null);
        transactions.add(trans);
        transactions.add(trans2);

        //When
        when(transactionRepository.findByCurrentMonth(any(TransactionType.class))).thenReturn(transactions);
        Double actualAmount = transactionService.getThisMonthExpense(1);

        //Then
        Assertions.assertEquals(expectedAmount,actualAmount);
    }

    @Test
    public void testGetThisMonthIncome(){
        //Given
        Double expectedAmount = 200.55;
        List<Transaction> transactions = new ArrayList<>();
        Transaction trans = new Transaction(null,null,null, null,100.00,null);
        Transaction trans2 = new Transaction(null,null,null, null,100.55,null);
        transactions.add(trans);
        transactions.add(trans2);

        //When
        when(transactionRepository.findByCurrentMonth(any(TransactionType.class))).thenReturn(transactions);
        Double actualAmount = transactionService.getThisMonthIncome(1);

        //Then
        Assertions.assertEquals(expectedAmount,actualAmount);
    }
}