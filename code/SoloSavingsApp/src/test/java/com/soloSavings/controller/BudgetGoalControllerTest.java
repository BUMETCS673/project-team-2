package com.soloSavings.controller;

import com.soloSavings.exceptions.BudgetGoalException;
import com.soloSavings.model.BudgetGoal;
import com.soloSavings.model.BudgetGoalTracker;
import com.soloSavings.model.User;
import com.soloSavings.model.helper.BudgetGoalType;
import com.soloSavings.service.BudgetGoalService;
import com.soloSavings.service.BudgetGoalTrackerService;
import com.soloSavings.service.SecurityContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BudgetGoalControllerTest {

    @Mock
    BudgetGoalService budgetGoalService;

    @Mock
    BudgetGoalTrackerService budgetGoalTrackerService;

    @Mock
    SecurityContext securityContext;

    @InjectMocks
    BudgetGoalController budgetGoalController;

    private User user;
    private BudgetGoal budgetGoal;
    private BudgetGoal budgetGoalError;
    private ResponseEntity<?> response;
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
        doNothing().when(securityContext).setContext(any(
                org.springframework.security.core.context.SecurityContext.class));
        when(securityContext.getCurrentUser()).thenReturn(user);
        doNothing().when(securityContext).dispose();
        budgetGoal = BudgetGoal.builder().id(1)
                .userId(1)
                .budgetGoalType(BudgetGoalType.SAVE)
                .source("Work")
                .targetAmount(1000.00)
                .startDate(null)
                .build();
        budgetGoalError = BudgetGoal.builder().id(2)
                .userId(1)
                .budgetGoalType(BudgetGoalType.SPEND)
                .source("Fun")
                .targetAmount(-500.00)
                .startDate(null)
                .build();
    }

    @Test
    public void testAddBudgetGoal() throws BudgetGoalException {
        //When
        when(budgetGoalService.addBudgetGoal(user.getUser_id(),budgetGoal)).thenReturn(budgetGoal);
        response = budgetGoalController.addBudgetGoal(budgetGoal);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(budgetGoal, response.getBody());
    }

    @Test
    public void testAddBudgetGoalError() throws BudgetGoalException {
        //When
        when(budgetGoalService.addBudgetGoal(user.getUser_id(),budgetGoalError)).thenThrow(new BudgetGoalException("Invalid amount."));
        response = budgetGoalController.addBudgetGoal(budgetGoalError);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals("Invalid amount.", response.getBody());
    }

    @Test
    public void testDeleteBudgetGoal() throws BudgetGoalException {
        //When
        when(budgetGoalService.deleteBudgetGoal(budgetGoal.getId())).thenReturn(budgetGoal);
        response = budgetGoalController.deleteBudgetGoal(budgetGoal.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(budgetGoal, response.getBody());
    }

    @Test
    public void testDeleteBudgetGoalError() throws BudgetGoalException {
        //When
        when(budgetGoalService.deleteBudgetGoal(budgetGoalError.getId())).thenThrow(new BudgetGoalException("Invalid amount."));
        response = budgetGoalController.deleteBudgetGoal(budgetGoalError.getId());

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
        assertEquals("Invalid amount.", response.getBody());
    }

    @Test
    public void testGetBudgetGoals() {
        //When
        List<BudgetGoalTracker> budgetGoalTrackerList = new ArrayList<>();
        BudgetGoalTracker budgetGoalTracker = BudgetGoalTracker.builder().id(1)
                .budgetGoalType(BudgetGoalType.SAVE)
                .source("Work")
                .targetAmount(1000.00)
                .actualAmount(200.00)
                .userId(1)
                .build();
        budgetGoalTrackerList.add(budgetGoalTracker);
        when(budgetGoalTrackerService.findAllGoalsByUserId(user.getUser_id())).thenReturn(budgetGoalTrackerList);
        response = budgetGoalController.getBudgetGoals();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(budgetGoalTrackerList, response.getBody());
    }

}
