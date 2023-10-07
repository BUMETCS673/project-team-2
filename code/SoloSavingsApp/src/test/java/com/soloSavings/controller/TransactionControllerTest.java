package com.soloSavings.controller;

import com.soloSavings.exceptions.TransactionException;
import com.soloSavings.model.Transaction;
import com.soloSavings.model.User;
import com.soloSavings.model.helper.TransactionType;
import com.soloSavings.service.SecurityContext;
import com.soloSavings.service.TransactionService;
import static org.mockito.ArgumentMatchers.any;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertTrue;
@SpringBootTest
public class TransactionControllerTest {

    @InjectMocks
    TransactionController transController;
    @Mock
    TransactionService transService;
    @Mock
    SecurityContext securityContext;
    User user;
    @BeforeEach
    void setup(){
        user = User.builder().user_id(1)
                .username("test")
                .email("test@gmail.com")
                .password_hash("passwordHash")
                .registration_date(null)
                .balance_amount(1000.00)
                .last_updated(null)
                .build();
        doNothing().when(securityContext).setContext(any(
                org.springframework.security.core.context.SecurityContext.class));
        when(securityContext.getCurrentUser()).thenReturn(user);
        doNothing().when(securityContext).dispose();
    }
    @Test
    public void testAddTransaction() throws TransactionException {
        Double balance = 100.00;
        Transaction tran = new Transaction(1,1,"", TransactionType.CREDIT,balance,null);

        when(transService.addTransaction(any(Integer.class),any(Transaction.class))).thenReturn(balance);
        ResponseEntity<?> response = transController.addTransaction(tran);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(balance, response.getBody());
    }

    @Test
    public void testAddTransactionWithInvalidRequest() throws TransactionException {
        Transaction transaction = new Transaction();
        String errorMessage = "Invalid transaction";

        // Mock
        when(transService.addTransaction(user.getUser_id(), transaction))
                .thenThrow(new TransactionException(errorMessage));

        ResponseEntity<?> response = transController.addTransaction(transaction);

        // Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
    }

    @Test
    public void testGetTransactionsByType() throws TransactionException {
        Transaction tran1 = new Transaction(1,1,"", TransactionType.CREDIT,100.00,null);
        Transaction tran2 = new Transaction(2,1,"", TransactionType.CREDIT,200.00,null);
        String transactionType = "expense";
        List<Transaction> trans = new ArrayList<>();
        trans.add(tran1); trans.add(tran2);

        // Mock
        when(transService.getTransactionsByType(user.getUser_id(), transactionType)).thenReturn(trans);
        ResponseEntity<?> response = transController.getTransactionsByType(transactionType);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(trans, response.getBody());
    }

    @Test
    public void testGetTransactionsByTypeWithInvalidRequest() throws TransactionException {
        String transactionType = "invalidType";
        String errorMessage = "Invalid transaction type";

        // Mock
        when(transService.getTransactionsByType(user.getUser_id(), transactionType))
                .thenThrow(new TransactionException(errorMessage));

        ResponseEntity<?> response = transController.getTransactionsByType(transactionType);

        // Assert
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
    }
    @Test
    public void testGetThisMonthIncome() throws TransactionException {
        Double thisMonthIncome = 1000.0; // Set your expected this month's income here

        // Mock
        when(transService.getThisMonthIncome(user.getUser_id())).thenReturn(thisMonthIncome);
        ResponseEntity<?> response = transController.getThisMonthIncome();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(thisMonthIncome, response.getBody());
    }
    @Test
    public void testGetThisMonthIncomeWithInvalidRequest() throws TransactionException {
        String errorMessage = "Invalid request";

        // Mock
        when(transService.getThisMonthIncome(user.getUser_id()))
                .thenThrow(new TransactionException(errorMessage));

        ResponseEntity<?> response = transController.getThisMonthIncome();

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
    }

    @Test
    public void testGetThisMonthExpense() throws TransactionException {
        // Arrange
        Double thisMonthExpense = 500.0;

        // Mock
        when(transService.getThisMonthExpense(user.getUser_id())).thenReturn(thisMonthExpense);
        ResponseEntity<?> response = transController.getThisMonthExpense();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(thisMonthExpense, response.getBody());
    }
    @Test
    public void testGetThisMonthExpenseWithInvalidRequest() throws TransactionException {
        String errorMessage = "Invalid request";

        // Mock
        when(transService.getThisMonthExpense(user.getUser_id()))
                .thenThrow(new TransactionException(errorMessage));

        ResponseEntity<?> response = transController.getThisMonthExpense();

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
    }
    
    
    @Test
    public void testGetTransactionsForUser() {
        // Initialize mockito annotations
        MockitoAnnotations.openMocks(this);

        // Mocked user ID
        Integer userId = 1;

        // Create a sample Transaction object
        Transaction sampleTransaction = new Transaction();
        sampleTransaction.setTransaction_id(1);
        sampleTransaction.setUser_id(userId);
        sampleTransaction.setSource("stealing");
        // ... Set other properties as needed

        // Mock the behavior of transactionServiceImpl
        when(transService.getTransactionsForUser(userId))
            .thenReturn(Optional.of(sampleTransaction));

        // Call the method you want to test
        Optional<Transaction> result = transService.getTransactionsForUser(userId);

        // Assertions
        assertTrue(result.isPresent()); // Check if the result is present (not empty)
        Transaction retrievedTransaction = result.get(); // Get the retrieved Transaction
        assertEquals(userId, retrievedTransaction.getUser_id());
        assertEquals("stealing", retrievedTransaction.getSource());
        // ... Add more assertions for other properties as needed
    }
}





 
