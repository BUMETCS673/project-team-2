package com.soloSavings.service;

import com.soloSavings.exceptions.BudgetGoalException;
import com.soloSavings.model.BudgetGoal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BudgetGoalService {
    List<BudgetGoal> findAllByUserIdCurrentMonth(Integer userId) throws BudgetGoalException;
    void addBudgetGoal(Integer userId, BudgetGoal budgetGoal) throws BudgetGoalException;

    void deleteBudgetGoal(Integer id) throws BudgetGoalException;
}
