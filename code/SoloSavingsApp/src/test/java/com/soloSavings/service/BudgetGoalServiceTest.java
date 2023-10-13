package com.soloSavings.service;

import com.soloSavings.exceptions.BudgetGoalException;
import com.soloSavings.model.BudgetGoal;
import com.soloSavings.model.User;
import com.soloSavings.model.helper.BudgetGoalType;
import com.soloSavings.repository.BudgetGoalRepository;
import com.soloSavings.repository.UserRepository;
import com.soloSavings.serviceImpl.BudgetGoalServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

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
    private BudgetGoal budgetGoalAdded;
    private BudgetGoal budgetGoalDeleted;

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
        budgetGoalAdded = budgetGoalService.addBudgetGoal(user.getUser_id(),budgetGoal);

        //Then
        assertEquals(budgetGoalAdded,budgetGoal);
    }

    @Test
    public void testAddBudgetGoalError() throws BudgetGoalException {
        //When
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(budgetGoalRepository.save(any(BudgetGoal.class))).thenReturn(budgetGoal);
        budgetGoal.setTargetAmount(-1.00);

        //Then
        Assertions.assertThrows(BudgetGoalException.class, () -> {
           budgetGoalService.addBudgetGoal(user.getUser_id(),budgetGoal);
        });
    }

    @Test
    public void testDeleteBudgetGoal() throws BudgetGoalException {
        //When
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(budgetGoalRepository.save(any(BudgetGoal.class))).thenReturn(budgetGoal);
        when(budgetGoalRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(budgetGoal));
        budgetGoalAdded = budgetGoalService.addBudgetGoal(user.getUser_id(),budgetGoal);
        budgetGoalDeleted = budgetGoalService.deleteBudgetGoal(user.getUser_id());

        //Then
        assertEquals(budgetGoalAdded,budgetGoal);
    }

    @Test
    public void testDeleteBudgetGoalError() throws BudgetGoalException {
        //When
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(budgetGoalRepository.save(any(BudgetGoal.class))).thenReturn(budgetGoal);

        //Then
        Assertions.assertThrows(BudgetGoalException.class, () -> {
            budgetGoalService.deleteBudgetGoal(user.getUser_id());
        });
    }
}
