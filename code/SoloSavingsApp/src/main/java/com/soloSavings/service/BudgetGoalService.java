package com.soloSavings.service;

import com.soloSavings.exceptions.BudgetGoalException;
import com.soloSavings.model.BudgetGoal;
import org.springframework.stereotype.Service;

@Service
public interface BudgetGoalService {
    BudgetGoal addBudgetGoal(Integer userId, BudgetGoal budgetGoal) throws BudgetGoalException;

    BudgetGoal deleteBudgetGoal(Integer id) throws BudgetGoalException;
}
