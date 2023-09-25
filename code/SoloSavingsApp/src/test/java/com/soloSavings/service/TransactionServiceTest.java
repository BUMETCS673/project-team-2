package com.soloSavings.service;

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

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
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
        assertEquals(expectedAmount,actualAmount);
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
    public void testAddExpenseInvalidAmount() throws TransactionException {
        //Given
        Integer user_id = 1;
        Transaction transaction = new Transaction(1, 1, "Expense", TransactionType.DEBIT, -1.00, LocalDate.now());

        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        //Then
        Assertions.assertThrows(TransactionException.class, () -> {
            transactionService.addTransaction(user_id, transaction);
        });
    }

    @Test
    public void testAddExpenseInSufficientBalance() throws TransactionException {
        //Given
        Integer user_id = 1;
        Transaction transaction = new Transaction(1, 1, "Expense", TransactionType.DEBIT, 1000.00, LocalDate.now());

        User mockUser = new User(1, "generic", "generic@solosavings.com", "password_hash", LocalDate.now(), 100.00, LocalDate.now());
        //When
        when(userRepository.findById(user_id)).thenReturn(Optional.of(mockUser));

        //Then
        Assertions.assertThrows(TransactionException.class, () -> {
            transactionService.addTransaction(user_id, transaction);
        });
    }

    @Test
    public void testAddExpense() throws TransactionException {
        //Given
        Integer user_id = 1;
        Transaction transaction = new Transaction(1, 1, "Expense", TransactionType.DEBIT, 50.00, LocalDate.now());

        User mockUserBefore = new User(1, "generic", "generic@solosavings.com", "password_hash", LocalDate.now(), 100.00, LocalDate.now());

        //When
        when(userRepository.findById(user_id)).thenReturn(Optional.of(mockUserBefore));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        when(userRepository.save(any(User.class))).thenReturn(mockUserBefore);
        Double expectedAmount = transactionService.addTransaction(1,transaction);

        //Then
        assertEquals(expectedAmount, mockUserBefore.getBalance_amount());
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
        assertEquals(expectedAmount,actualAmount);
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
        assertEquals(expectedAmount,actualAmount);
    }

    @Test
    public void testGetTransactionsByTypeCredit() throws TransactionException {
        List<Transaction> creditTransactions = new ArrayList<>();
        Transaction trans = new Transaction(1,1,null, TransactionType.CREDIT,100.00,null);
        Transaction trans2 = new Transaction(2,1,null, TransactionType.CREDIT,100.55,null);
        creditTransactions.add(trans); creditTransactions.add(trans2);
        // Mock
        when(transactionRepository.findByTransactionType(1, TransactionType.CREDIT))
                .thenReturn(creditTransactions);

        // Act
        List<Transaction> result = transactionService.getTransactionsByType(1, "CREDIT");

        // Assert
        assertEquals(creditTransactions, result);
    }

    @Test
    public void testGetTransactionsByTypeDebit() throws TransactionException {
        List<Transaction> debitTransactions = new ArrayList<>(); // Initialize your debit transactions
        Transaction trans = new Transaction(1,1,null, TransactionType.DEBIT,100.00,null);
        Transaction trans2 = new Transaction(2,1,null, TransactionType.DEBIT,100.55,null);
        debitTransactions.add(trans); debitTransactions.add(trans2);
        // Mock the behavior of your repository for debit transactions
        when(transactionRepository.findByTransactionType(1, TransactionType.DEBIT))
                .thenReturn(debitTransactions);

        // Act
        List<Transaction> result = transactionService.getTransactionsByType(1, "DEBIT");

        // Assert
        assertEquals(debitTransactions, result);
    }

    @Test
    public void testGetTransactionsByTypeInvalid() throws TransactionException {
        Integer userId = 1;
        String transactionType = "INVALID_TYPE";

        Assertions.assertThrows(TransactionException.class, () -> {
            transactionService.getTransactionsByType(userId, transactionType);
        });
    }

}