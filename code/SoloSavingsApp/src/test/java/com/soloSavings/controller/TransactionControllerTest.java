package com.soloSavings.controller;

import com.soloSavings.exceptions.TransactionException;
import com.soloSavings.model.Transaction;
import com.soloSavings.model.User;
import com.soloSavings.model.helper.TransactionType;
import com.soloSavings.service.SecurityContext;
import com.soloSavings.service.TransactionService;
import static com.soloSavings.utils.Constants.INTERNAL_SERVER_ERROR;
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
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
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
                .balance_amount(10.00)
                .last_updated(null)
                .build();
        doNothing().when(securityContext).setContext(any(
                org.springframework.security.core.context.SecurityContext.class));
        when(securityContext.getCurrentUser()).thenReturn(user);
        doNothing().when(securityContext).dispose();
    }
    @Test
    public void testAddTrandaction() throws TransactionException {
        Double balance = 100.00;
        Transaction tran = new Transaction(1,1,"", TransactionType.CREDIT,balance,null);

        when(transService.addTransaction(user.getUser_id(),tran)).thenReturn(balance);
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
        when(transService.getThisMonthTotalAmount(anyInt(),any(TransactionType.class))).thenReturn(thisMonthIncome);
        ResponseEntity<?> response = transController.getThisMonthTotalAmount(TransactionType.CREDIT);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(thisMonthIncome, response.getBody());
    }
    @Test
    public void testGetThisMonthIncomeWithInvalidRequest() throws TransactionException {
        // Mock
        when(transService.getThisMonthTotalAmount(anyInt(),any(TransactionType.class)))
                .thenThrow(new TransactionException(INTERNAL_SERVER_ERROR));

        ResponseEntity<?> response = transController.getThisMonthTotalAmount(TransactionType.CREDIT);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(INTERNAL_SERVER_ERROR, response.getBody());
    }

    @Test
    public void testGetThisMonthExpense() throws TransactionException {
        // Arrange
        Double thisMonthExpense = 500.0;

        // Mock
        when(transService.getThisMonthTotalAmount(anyInt(),any(TransactionType.class))).thenReturn(thisMonthExpense);
        ResponseEntity<?> response = transController.getThisMonthTotalAmount(TransactionType.DEBIT);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(thisMonthExpense, response.getBody());
    }
    @Test
    public void testGetThisMonthExpenseWithInvalidRequest() throws TransactionException {
        // Mock
        when(transService.getThisMonthTotalAmount(anyInt(),any(TransactionType.class)))
                .thenThrow(new TransactionException(INTERNAL_SERVER_ERROR));

        ResponseEntity<?> response = transController.getThisMonthTotalAmount(TransactionType.DEBIT);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(INTERNAL_SERVER_ERROR, response.getBody());
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
            .thenReturn(List.of(sampleTransaction));

        // Call the method you want to test
        List<Transaction> result = transService.getTransactionsForUser(userId);

        // Assertions
        assertTrue(!result.isEmpty()); // Check if the result is present (not empty)
        Transaction retrievedTransaction = result.get(0); // Get the retrieved Transaction
        assertEquals(userId, retrievedTransaction.getUser_id());
        assertEquals("stealing", retrievedTransaction.getSource());
        // ... Add more assertions for other properties as needed
    }

    @Test
    public void testGetMonthlyAnalyticsByYearError() throws TransactionException {
        // Mock
        when(transService.getMonthlyAnalyticsByYear(anyInt(),anyInt(),any(TransactionType.class)))
                .thenThrow(new TransactionException(INTERNAL_SERVER_ERROR));

        ResponseEntity<?> response = transController.getMonthlyAnalyticsByYear(TransactionType.DEBIT,2023);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(INTERNAL_SERVER_ERROR, response.getBody());
    }

    @Test
    public void testGetMonthlyAnalyticsByYear() throws TransactionException {
        List<Map<Object, Object>> list = new ArrayList<>();

        // Mock
        when(transService.getMonthlyAnalyticsByYear(anyInt(),anyInt(),any(TransactionType.class)))
                .thenReturn(list);

        ResponseEntity<?> response = transController.getMonthlyAnalyticsByYear(TransactionType.DEBIT,2023);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(list, response.getBody());
    }
}





 
