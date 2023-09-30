package com.soloSavings.integration;

import com.soloSavings.model.Transaction;
import com.soloSavings.model.User;
import com.soloSavings.model.helper.TransactionType;
import com.soloSavings.repository.TransactionRepository;
import com.soloSavings.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@Profile("test")
public class TransactionApiIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    private User commonUser;
    @BeforeEach
    public void setup(){
        String commonUserEmail = "common@email";
        if(null != userRepository.findUserByEmail(commonUserEmail)){
            userRepository.delete(userRepository.findByUsername(commonUserEmail));
        }
        commonUser = new User(null,"common",commonUserEmail,"Password1",LocalDate.now(),1000.00,LocalDate.now());
        commonUser = userRepository.save(commonUser);
    }
    @AfterEach
    public void cleanup(){
        List<Transaction> trans = transactionRepository.findAllByUserId(commonUser.getUser_id());
        if(null != trans){
            transactionRepository.deleteAll(trans);
        }
        userRepository.delete(commonUser);
    }
    @Test
    public void testAddIncomeTransaction() {
        Transaction transaction = new Transaction(null,commonUser.getUser_id(),"", TransactionType.CREDIT,100.0, LocalDate.now());
        Double expectedIncome = commonUser.getBalance_amount() + 100.0;

        ResponseEntity<Double> response = restTemplate.postForEntity("/transaction/add/{id}", transaction, Double.class, commonUser.getUser_id());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedIncome);
    }

    @Test
    public void testAddIncomeTransactionInvalidAmount() {
        Transaction transaction = new Transaction(null,commonUser.getUser_id(),"", TransactionType.CREDIT,-100.0, LocalDate.now());

        ResponseEntity<String> response = restTemplate.postForEntity("/transaction/add/{id}", transaction, String.class, commonUser.getUser_id());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(response.getBody()).isEqualTo("Invalid Income Amount!");
    }

    @Test
    public void testAddExpenseTransaction() {
        Transaction transaction = new Transaction(null,commonUser.getUser_id(),"", TransactionType.DEBIT,100.0, LocalDate.now());
        Double expectedExpense = commonUser.getBalance_amount() - 100.0;

        ResponseEntity<Double> response = restTemplate.postForEntity("/transaction/add/{id}", transaction, Double.class, commonUser.getUser_id());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedExpense);
    }
    @Test
    public void testAddExpenseTransactionInvalidAmount() {
        Double invalidExpense = commonUser.getBalance_amount() + 1;
        Transaction transaction = new Transaction(null,commonUser.getUser_id(),"", TransactionType.DEBIT,invalidExpense, LocalDate.now());

        ResponseEntity<String> response = restTemplate.postForEntity("/transaction/add/{id}", transaction, String.class, commonUser.getUser_id());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(response.getBody()).isEqualTo("Insufficient Account Balance for Expense Amount!");
    }

    @Test
    public void testGetTransactionByTypeDebit() {
        Transaction tran1 = new Transaction(null,commonUser.getUser_id(),"", TransactionType.DEBIT,150.0, LocalDate.now());
        Transaction tran2 = new Transaction(null,commonUser.getUser_id(),"", TransactionType.DEBIT,150.0, LocalDate.now());
        Transaction tran3 = new Transaction(null,commonUser.getUser_id(),"", TransactionType.CREDIT,150.0, LocalDate.now());
        transactionRepository.save(tran1);
        transactionRepository.save(tran2);
        transactionRepository.save(tran3);
        Integer expectedNumDebit = 2;

        ResponseEntity<List> response = restTemplate.getForEntity("/transaction/{user_id}/{transaction_type}", List.class, commonUser.getUser_id(),"DEBIT");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isEqualTo(expectedNumDebit);
    }

    @Test
    public void testGetTransactionByTypeCredit() {
        Transaction tran1 = new Transaction(null,commonUser.getUser_id(),"", TransactionType.CREDIT,150.0, null);
        Transaction tran2 = new Transaction(null,commonUser.getUser_id(),"", TransactionType.CREDIT,150.0, null);
        Transaction tran3 = new Transaction(null,commonUser.getUser_id(),"", TransactionType.DEBIT,150.0, null);
        transactionRepository.save(tran1);
        transactionRepository.save(tran2);
        transactionRepository.save(tran3);
        Integer expectedNumCredit = 2;

        ResponseEntity<List> response = restTemplate.getForEntity("/transaction/{user_id}/{transaction_type}", List.class, commonUser.getUser_id(),"CREDIT");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isEqualTo(expectedNumCredit);
    }
    @Test
    public void testGetTransactionByTypeWithInvalidType() {
        String invalidTransactionType = "WRONG";
        ResponseEntity<String> response = restTemplate.getForEntity("/transaction/{user_id}/{transaction_type}", String.class, 0,invalidTransactionType);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void testGetThisMonthExpense() {
        Transaction tran1 = new Transaction(null,commonUser.getUser_id(),"", TransactionType.DEBIT,150.0, LocalDate.now());
        Transaction tran2 = new Transaction(null,commonUser.getUser_id(),"", TransactionType.DEBIT,150.0, null);
        transactionRepository.save(tran1);
        transactionRepository.save(tran2);
        Double expectedExpense = 150.0;

        ResponseEntity<Double> response = restTemplate.getForEntity("/transaction/monthly/expense/{user_id}", Double.class, commonUser.getUser_id());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedExpense);
    }

    @Test
    public void testGetThisMonthIncome() {
        Transaction tran1 = new Transaction(null,commonUser.getUser_id(),"", TransactionType.CREDIT,150.0, LocalDate.now());
        Transaction tran2 = new Transaction(null,commonUser.getUser_id(),"", TransactionType.CREDIT,150.0, null);
        transactionRepository.save(tran1);
        transactionRepository.save(tran2);
        Double expectedIncome = 150.0;

        ResponseEntity<Double> response = restTemplate.getForEntity("/transaction/monthly/income/{user_id}", Double.class, commonUser.getUser_id());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedIncome);
    }
}
