package com.soloSavings.service;

import com.soloSavings.model.BudgetGoal;
import com.soloSavings.model.BudgetGoalTracker;
import com.soloSavings.model.Transaction;
import com.soloSavings.model.User;
import com.soloSavings.model.helper.BudgetGoalType;
import com.soloSavings.model.helper.TransactionType;
import com.soloSavings.repository.BudgetGoalRepository;
import com.soloSavings.repository.BudgetGoalTrackerRepository;
import com.soloSavings.repository.TransactionRepository;
import com.soloSavings.repository.UserRepository;
import com.soloSavings.serviceImpl.BudgetGoalTrackerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BudgetGoalTrackerServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    BudgetGoalRepository budgetGoalRepository;

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    BudgetGoalTrackerRepository budgetGoalTrackerRepository;

    @InjectMocks
    BudgetGoalTrackerServiceImpl budgetGoalTrackerService;

    private User user;
    private BudgetGoal budgetGoalSPEND;
    private BudgetGoal budgetGoalSAVE;
    private Transaction t1;
    private Transaction t2;
    private Transaction t3;
    private Transaction t4;
    private BudgetGoalTracker budgetGoalTrackerSPEND;
    private BudgetGoalTracker budgetGoalTrackerSAVE;
    private List<BudgetGoalTracker> budgetGoalTrackerList;

    @BeforeEach
    void setup(){
        user = User.builder().user_id(1)
                .username("test")
                .email("test@gmail.com")
                .password_hash("passwordHash")
                .registration_date(null)
                .balance_amount(0.00)
                .last_updated(null)
                .build();
        t1 = Transaction.builder().transaction_id(1)
                .user_id(1)
                .source("Work")
                .transaction_type(TransactionType.CREDIT)
                .amount(100.00)
                .transaction_date(null)
                .build();
        t2 = Transaction.builder().transaction_id(2)
                .user_id(1)
                .source("Work")
                .transaction_type(TransactionType.CREDIT)
                .amount(100.00)
                .transaction_date(null)
                .build();
        t3 = Transaction.builder().transaction_id(3)
                .user_id(1)
                .source("Fun")
                .transaction_type(TransactionType.DEBIT)
                .amount(100.00)
                .transaction_date(null)
                .build();
        t4 = Transaction.builder().transaction_id(4)
                .user_id(1)
                .source("Fun")
                .transaction_type(TransactionType.DEBIT)
                .amount(100.00)
                .transaction_date(null)
                .build();
        budgetGoalSAVE = BudgetGoal.builder().id(1)
                .userId(1)
                .budgetGoalType(BudgetGoalType.SAVE)
                .source("Work")
                .targetAmount(1000.00)
                .startDate(null)
                .build();
        budgetGoalSPEND = BudgetGoal.builder().id(2)
                .userId(1)
                .budgetGoalType(BudgetGoalType.SPEND)
                .source("Fun")
                .targetAmount(500.00)
                .startDate(null)
                .build();
        budgetGoalTrackerSAVE = BudgetGoalTracker.builder().id(1)
                .budgetGoalType(BudgetGoalType.SAVE)
                .source("Work")
                .targetAmount(1000.00)
                .actualAmount(200.00)
                .userId(1)
                .build();
        budgetGoalTrackerSPEND = BudgetGoalTracker.builder().id(2)
                .budgetGoalType(BudgetGoalType.SPEND)
                .source("Fun")
                .targetAmount(1000.00)
                .actualAmount(200.00)
                .userId(1)
                .build();
        budgetGoalTrackerList = new ArrayList<>();
        budgetGoalTrackerList.add(budgetGoalTrackerSAVE);
        budgetGoalTrackerList.add(budgetGoalTrackerSPEND);
    }

    @Test
    public void testFindAllGoalsByUserId() {
        //When
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(transactionRepository.save(t1)).thenReturn(t1);
        when(transactionRepository.save(t2)).thenReturn(t2);
        when(transactionRepository.save(t3)).thenReturn(t3);
        when(transactionRepository.save(t4)).thenReturn(t4);
        when(budgetGoalRepository.save(budgetGoalSPEND)).thenReturn(budgetGoalSPEND);
        when(budgetGoalRepository.save(budgetGoalSAVE)).thenReturn(budgetGoalSAVE);
        when(budgetGoalTrackerRepository.findAllGoalsByUserId(user.getUser_id())).thenReturn(budgetGoalTrackerList);
        List<BudgetGoalTracker> budgetGoalTrackerListReturned = budgetGoalTrackerService.findAllGoalsByUserId(user.getUser_id());

        //Then
        assertEquals(budgetGoalTrackerListReturned,budgetGoalTrackerList);

    }

}
