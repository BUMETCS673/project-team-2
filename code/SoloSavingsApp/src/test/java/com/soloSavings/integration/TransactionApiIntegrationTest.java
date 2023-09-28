package com.soloSavings.integration;

import com.soloSavings.model.Transaction;
import com.soloSavings.model.User;
import com.soloSavings.model.helper.TransactionType;
import com.soloSavings.repository.TransactionRepository;
import com.soloSavings.repository.UserRepository;
import com.soloSavings.service.TransactionService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcPrint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class TransactionApiIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    public void testAddIncomeTransaction() {
        User user = new User(null,"test1","test2@email","Password1",LocalDate.now(),100.00,LocalDate.now());
        user = userRepository.save(user);
        Transaction transaction = new Transaction(null,user.getUser_id(),"", TransactionType.CREDIT,100.0, LocalDate.now());

        ResponseEntity<Double> response = restTemplate.postForEntity("/transaction/add/{id}", transaction, Double.class, user.getUser_id());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(200.00);
        userRepository.delete(user);
    }

    @Test
    public void testAddIncomeTransactionInvalidAmount() {
        User user = new User(null,"","","",null,100.00,null);
        user =  userRepository.save(user);
        Transaction transaction = new Transaction(null,user.getUser_id(),"", TransactionType.CREDIT,-100.0, LocalDate.now());

        ResponseEntity<String> response = restTemplate.postForEntity("/transaction/add/{id}", transaction, String.class, user.getUser_id());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(response.getBody()).isEqualTo("Invalid Income Amount!");
        userRepository.delete(user);
    }

    @Test
    public void testAddExpenseTransaction() {
        User user = new User(null,"test3","test3@email","Password3",LocalDate.now(),200.00,LocalDate.now());
        user = userRepository.save(user);
        Transaction transaction = new Transaction(null,user.getUser_id(),"source3", TransactionType.DEBIT,50.00, LocalDate.now());

        ResponseEntity<Double> response = restTemplate.postForEntity("/transaction/add/{id}", transaction,Double.class,user.getUser_id());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(150.0);
        userRepository.delete(user);
    }
    @Test
    public void testAddExpenseTransactionInvalidAmount() {
        User user = new User(null,"","","",null,100.00,null);
        user = userRepository.save(user);
        Transaction transaction = new Transaction(null,user.getUser_id(),"", TransactionType.DEBIT,150.0, LocalDate.now());

        ResponseEntity<String> response = restTemplate.postForEntity("/transaction/add/{id}", transaction, String.class, user.getUser_id());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(response.getBody()).isEqualTo("Insufficient Account Balance for Expense Amount!");
        userRepository.delete(user);
    }

    @Test
    public void testGetTransactionByTypeDebit() {
        User user = new User(null,"test5","test5@email","Password5",LocalDate.now(),1000.00,LocalDate.now());
        user = userRepository.save(user);
        Transaction tran1 = new Transaction(null,user.getUser_id(),"", TransactionType.DEBIT,150.0, LocalDate.now());
        Transaction tran2 = new Transaction(null,user.getUser_id(),"", TransactionType.DEBIT,150.0, LocalDate.now());
        Transaction tran3 = new Transaction(null,user.getUser_id(),"", TransactionType.CREDIT,150.0, LocalDate.now());
        transactionRepository.save(tran1);
        transactionRepository.save(tran2);

        ResponseEntity<List> response = restTemplate.getForEntity("/transaction/{user_id}/{transaction_type}", List.class, user.getUser_id(),"DEBIT");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isEqualTo(2);
        transactionRepository.delete(tran1);
        transactionRepository.delete(tran2);
    }

    @Test
    public void testGetTransactionByTypeCredit() {
        User user = new User(null,"test6","test6@email","Password6",LocalDate.now(),1000.00,LocalDate.now());
        user = userRepository.save(user);
        Transaction tran1 = new Transaction(null,user.getUser_id(),"", TransactionType.CREDIT,150.0, null);
        Transaction tran2 = new Transaction(null,user.getUser_id(),"", TransactionType.CREDIT,150.0, null);
        Transaction tran3 = new Transaction(null,user.getUser_id(),"", TransactionType.DEBIT,150.0, null);
        transactionRepository.save(tran1);
        transactionRepository.save(tran2);
        transactionRepository.save(tran3);

        ResponseEntity<List> response = restTemplate.getForEntity("/transaction/{user_id}/{transaction_type}", List.class, user.getUser_id(),"CREDIT");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isEqualTo(2);
        transactionRepository.delete(tran1);
        transactionRepository.delete(tran2);
    }
    @Test
    public void testGetTransactionByTypeWithInvalidType() {
        ResponseEntity<String> response = restTemplate.getForEntity("/transaction/{user_id}/{transaction_type}", String.class, 0,"WRONG");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void testGetThisMonthExpense() {
        User user = new User(null,"test5","test5@email","Password5",LocalDate.now(),1000.00,LocalDate.now());
        user = userRepository.save(user);
        Transaction tran1 = new Transaction(null,user.getUser_id(),"", TransactionType.DEBIT,150.0, LocalDate.now());
        Transaction tran2 = new Transaction(null,user.getUser_id(),"", TransactionType.DEBIT,150.0, null);
        transactionRepository.save(tran1);
        transactionRepository.save(tran2);

        ResponseEntity<Double> response = restTemplate.getForEntity("/transaction/monthly/expense/{user_id}", Double.class, user.getUser_id());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(150);
        transactionRepository.delete(tran1);
        transactionRepository.delete(tran2);
    }

    @Test
    public void testGetThisMonthIncome() {
        User user = new User(null,"test5","test5@email","Password5",LocalDate.now(),1000.00,LocalDate.now());
        user = userRepository.save(user);
        Transaction tran1 = new Transaction(null,user.getUser_id(),"", TransactionType.CREDIT,150.0, LocalDate.now());
        Transaction tran2 = new Transaction(null,user.getUser_id(),"", TransactionType.CREDIT,150.0, null);
        transactionRepository.save(tran1);
        transactionRepository.save(tran2);

        ResponseEntity<Double> response = restTemplate.getForEntity("/transaction/monthly/income/{user_id}", Double.class, user.getUser_id());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(150);
        transactionRepository.delete(tran1);
        transactionRepository.delete(tran2);
    }
}
