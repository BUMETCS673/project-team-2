package com.soloSavings.integration;

import com.soloSavings.model.Login;
import com.soloSavings.model.Transaction;
import com.soloSavings.model.User;
import com.soloSavings.model.helper.TransactionType;
import com.soloSavings.repository.TransactionRepository;
import com.soloSavings.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
public class UserApiIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    private User commonUser;

    @BeforeEach
    public void setCommonUser(){
        String commonUserEmail = "common@email";
        if(null != userRepository.findUserByEmail(commonUserEmail)){
            userRepository.delete(userRepository.findByUsername(commonUserEmail));
        }
        commonUser = new User(null,"common",commonUserEmail,"Password1",LocalDate.now(),1000.00,LocalDate.now());
        commonUser = userRepository.save(commonUser);
    }
    @AfterEach
    public void deleteTransactionsAndUser(){
        List<Transaction> trans = transactionRepository.findAllByUserId(commonUser.getUser_id());
        if(null != trans){
            transactionRepository.deleteAll(trans);
        }
        userRepository.delete(commonUser);
    }
    @Test
    public void testGetTotalBalance() {
        Transaction transaction = new Transaction(null,commonUser.getUser_id(),"", TransactionType.CREDIT,100.0, LocalDate.now());
        Double expectedBalance = commonUser.getBalance_amount() + 100.0;

        restTemplate.postForEntity("/transaction/add/{id}", transaction, Double.class, commonUser.getUser_id());
        ResponseEntity<Double> response2 = restTemplate.getForEntity("/user/balance/{id}", Double.class, commonUser.getUser_id());

        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response2.getBody()).isEqualTo(expectedBalance);
    }

    @Test
    public void testRegister() {
        User newUser = new User(null,"newUser","newUser@gmail","Password1",LocalDate.now(),1000.00,LocalDate.now());
        String expectedMessage = "user registered";

        ResponseEntity<String> response = restTemplate.postForEntity("/api/register", newUser, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedMessage);
        userRepository.delete(userRepository.findByUsername("newUser"));
    }

    @Test
    public void testRegisterAlreadyExistError() {
        String expectedMessage = String.format("email %s already registered",commonUser.getEmail());

        ResponseEntity<String> response = restTemplate.postForEntity("/api/register", commonUser, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getBody()).isEqualTo(expectedMessage);
    }

    @Test
    public void testLogin() {
        User loginUser = new User(null,"loginUser","loginUser@gmail","Password1",LocalDate.now(),1000.00,LocalDate.now());
        Login login = new Login(loginUser.getEmail(),loginUser.getPassword_hash());
        String expectedMessage = "user registered";

        ResponseEntity<String> registerResponse = restTemplate.postForEntity("/api/register", loginUser, String.class);
        ResponseEntity<String> loginResponse = restTemplate.postForEntity("/api/login", login, String.class);

        assertThat(registerResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(registerResponse.getBody()).isEqualTo(expectedMessage);

        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        userRepository.delete(userRepository.findByUsername(loginUser.getUsername()));
    }
}
