package com.soloSavings.controller;

import com.soloSavings.exceptions.TransactionException;
import com.soloSavings.model.Transaction;
import com.soloSavings.model.helper.TransactionType;
import com.soloSavings.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest
public class TransactionControllerTest {

    @InjectMocks
    TransactionController transController;
    @Mock
    TransactionService transService;

    @Test
    public void testAddTrandaction() throws TransactionException {
        Double balance = 100.00;
        Transaction tran = new Transaction(1,1,"", TransactionType.CREDIT,balance,null);
        transController.addTransaction(1,tran);

        when(transService.addTransaction(1,tran)).thenReturn(balance);
        ResponseEntity<?> response = transController.addTransaction(1,tran);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(balance, response.getBody());
    }

    @Test
    public void testAddTransactionWithInvalidRequest() throws TransactionException {
        Integer userId = 1;
        Transaction transaction = new Transaction();
        String errorMessage = "Invalid transaction";

        // Mock
        when(transService.addTransaction(userId, transaction))
                .thenThrow(new TransactionException(errorMessage));

        ResponseEntity<?> response = transController.addTransaction(userId, transaction);

        // Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
    }

    @Test
    public void testGetTransactionsByType() throws TransactionException {
        Integer userId = 1;
        Transaction tran1 = new Transaction(1,1,"", TransactionType.CREDIT,100.00,null);
        Transaction tran2 = new Transaction(2,1,"", TransactionType.CREDIT,200.00,null);
        String transactionType = "expense";
        List<Transaction> trans = new ArrayList<>();
        trans.add(tran1); trans.add(tran2);

        // Mock
        when(transService.getTransactionsByType(userId, transactionType)).thenReturn(trans);
        ResponseEntity<?> response = transController.getTransactionsByType(userId, transactionType);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(trans, response.getBody());
    }

    @Test
    public void testGetTransactionsByTypeWithInvalidRequest() throws TransactionException {
        Integer userId = 1;
        String transactionType = "invalidType";
        String errorMessage = "Invalid transaction type";

        // Mock
        when(transService.getTransactionsByType(userId, transactionType))
                .thenThrow(new TransactionException(errorMessage));

        ResponseEntity<?> response = transController.getTransactionsByType(userId, transactionType);

        // Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
    }
    @Test
    public void testGetThisMonthIncome() throws TransactionException {
        Integer userId = 1;
        Double thisMonthIncome = 1000.0; // Set your expected this month's income here

        // Mock
        when(transService.getThisMonthIncome(userId)).thenReturn(thisMonthIncome);
        ResponseEntity<?> response = transController.getThisMonthIncome(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(thisMonthIncome, response.getBody());
    }
    @Test
    public void testGetThisMonthIncomeWithInvalidRequest() throws TransactionException {
        Integer userId = 1;
        String errorMessage = "Invalid request";

        // Mock
        when(transService.getThisMonthIncome(userId))
                .thenThrow(new TransactionException(errorMessage));

        ResponseEntity<?> response = transController.getThisMonthIncome(userId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
    }

    @Test
    public void testGetThisMonthExpense() throws TransactionException {
        // Arrange
        Integer userId = 1;
        Double thisMonthExpense = 500.0;

        // Mock
        when(transService.getThisMonthExpense(userId)).thenReturn(thisMonthExpense);
        ResponseEntity<?> response = transController.getThisMonthExpense(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(thisMonthExpense, response.getBody());
    }
    @Test
    public void testGetThisMonthExpenseWithInvalidRequest() throws TransactionException {
        Integer userId = 1;
        String errorMessage = "Invalid request";

        // Mock
        when(transService.getThisMonthExpense(userId))
                .thenThrow(new TransactionException(errorMessage));

        ResponseEntity<?> response = transController.getThisMonthExpense(userId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
    }
}
