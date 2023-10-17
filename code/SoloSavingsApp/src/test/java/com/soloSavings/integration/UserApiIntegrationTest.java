package com.soloSavings.integration;

import com.soloSavings.model.Login;
import com.soloSavings.model.TokenDetails;
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
import org.springframework.http.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@Profile("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserApiIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    private User commonUser;
    HttpHeaders headers;
    @BeforeAll
    public void setUpUserAuth(){
        String commonUserEmail = "common@gmail.com";
        commonUser = new User(null,"common",commonUserEmail,"Password1",LocalDate.now(),0.0,LocalDate.now());
        restTemplate.postForEntity("/api/register", commonUser, String.class);

        Login login = new Login(commonUser.getUsername(),commonUser.getPassword_hash());

        ResponseEntity<TokenDetails> loginResponse = restTemplate.postForEntity("/api/login", login, TokenDetails.class);

        headers = new HttpHeaders();
        headers.setBearerAuth(Objects.requireNonNull(loginResponse.getBody().token()));
    }
    @BeforeEach
    public void setCommonUser(){
        commonUser = userRepository.findByUsername(commonUser.getUsername());
    }
    @Test
    public void testGetTotalBalance() {
        Transaction transaction = new Transaction(null,commonUser.getUser_id(),"", TransactionType.CREDIT,100.0, LocalDate.now());
        Double expectedBalance = commonUser.getBalance_amount() + 100.0;

        HttpEntity<Transaction> request = new HttpEntity<>(transaction, headers);
        restTemplate.postForEntity("/api/transaction/add", request, Double.class, commonUser.getUser_id());

        HttpEntity<Transaction> request2 = new HttpEntity<>(headers);
        ResponseEntity<Double> response2 = restTemplate.exchange("/api/user/balance", HttpMethod.GET,request2, Double.class);

        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response2.getBody()).isEqualTo(expectedBalance);
    }

    @Test
    public void testRegister() {
        User newUser = new User(null,"newUser","newUser@gmail","Password1",LocalDate.now(),1000.00,LocalDate.now());
        String expectedMessage = "The user account with email has successfully created";

        ResponseEntity<String> response = restTemplate.postForEntity("/api/register", newUser, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedMessage);
        userRepository.delete(userRepository.findByUsername("newUser"));
    }

    @Test
    public void testRegisterAlreadyExistError() {
        String expectedMessage = String.format("The email has already registered.");

        ResponseEntity<String> response = restTemplate.postForEntity("/api/register", commonUser, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getBody()).isEqualTo(expectedMessage);
    }

    @Test
    public void testLogin() {
        User loginUser = new User(null,"loginUser","loginUser@gmail","Password1",LocalDate.now(),1000.00,LocalDate.now());
        Login login = new Login(loginUser.getUsername(),loginUser.getPassword_hash());
        String expectedMessage = String.format("The user account with email has successfully created");

        ResponseEntity<String> registerResponse = restTemplate.postForEntity("/api/register", loginUser, String.class);
        ResponseEntity<String> loginResponse = restTemplate.postForEntity("/api/login", login, String.class);

        assertThat(registerResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(registerResponse.getBody()).isEqualTo(expectedMessage);

        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        userRepository.delete(userRepository.findByUsername(loginUser.getUsername()));
    }
}
