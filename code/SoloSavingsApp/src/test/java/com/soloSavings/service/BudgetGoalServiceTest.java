package com.soloSavings.service;

import com.soloSavings.exceptions.BudgetGoalException;
import com.soloSavings.model.BudgetGoal;
import com.soloSavings.model.User;
import com.soloSavings.model.helper.BudgetGoalType;
import com.soloSavings.repository.BudgetGoalRepository;
import com.soloSavings.repository.UserRepository;
import com.soloSavings.serviceImpl.BudgetGoalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class BudgetGoalServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    BudgetGoalRepository budgetGoalRepository;

    @InjectMocks
    BudgetGoalServiceImpl budgetGoalService;

    private User user;
    private BudgetGoal budgetGoal;
    private BudgetGoal budgetGoalReturned;

    @BeforeEach void setup(){
        //Given
        user = User.builder().user_id(1)
                .username("test")
                .email("test@gmail.com")
                .password_hash("passwordHash")
                .registration_date(null)
                .balance_amount(10.00)
                .last_updated(null)
                .build();
        budgetGoal = BudgetGoal.builder().id(1)
                .userId(1)
                .budgetGoalType(BudgetGoalType.SAVE)
                .source("Work")
                .targetAmount(500.00)
                .startDate(null)
                .build();
    }

    @Test
    public void testAddBudgetGoal() throws BudgetGoalException {
        //When
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(budgetGoalRepository.save(any(BudgetGoal.class))).thenReturn(budgetGoal);
        budgetGoalReturned = budgetGoalService.addBudgetGoal(user.getUser_id(),budgetGoal);

        //Then
        assertEquals(budgetGoalReturned,budgetGoal);
    }
}
