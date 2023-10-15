package com.soloSavings.serviceImpl;

import com.soloSavings.exceptions.BudgetGoalException;
import com.soloSavings.model.BudgetGoal;
import com.soloSavings.repository.BudgetGoalRepository;
import com.soloSavings.service.BudgetGoalService;
import com.soloSavings.utils.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class BudgetGoalServiceImpl implements BudgetGoalService {
    private final BudgetGoalRepository budgetGoalRepository;

    @Autowired
    public BudgetGoalServiceImpl(BudgetGoalRepository budgetGoalRepository) {
        this.budgetGoalRepository = budgetGoalRepository;
    }

    @Override
    public BudgetGoal addBudgetGoal(Integer userId, BudgetGoal budgetGoal) throws BudgetGoalException {
        if(Validation.validateBudgetGoal(budgetGoal.getTargetAmount())){
            budgetGoal.setUserId(userId);
            budgetGoal.setStartDate(LocalDate.now());
            budgetGoalRepository.save(budgetGoal);
            return budgetGoal;
        }else {
            throw new BudgetGoalException("Invalid budget goal target amount. Please input correct transaction amount!");
        }
    }

    @Override
    public BudgetGoal deleteBudgetGoal(Integer id) throws BudgetGoalException {
        BudgetGoal budgetGoal = budgetGoalRepository.findById(id).orElseThrow(() -> new BudgetGoalException("Budget goal not found!"));
        budgetGoalRepository.deleteById(id);
        return budgetGoal;
    }


}