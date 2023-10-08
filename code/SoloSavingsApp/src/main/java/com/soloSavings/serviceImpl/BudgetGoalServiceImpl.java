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
    public List<BudgetGoal> findAllByUserIdCurrentMonth(Integer userId) throws BudgetGoalException {
        return budgetGoalRepository.findAllByUserIdCurrentMonth(userId);
    }

    @Override
    public void addBudgetGoal(Integer userId, BudgetGoal budgetGoal) throws BudgetGoalException {
        if(Validation.validateBudgetGoal(budgetGoal.getTargetAmount())){
            budgetGoal.setUserId(userId);
            budgetGoal.setStartDate(LocalDate.now());
            budgetGoalRepository.save(budgetGoal);
        }else {
            throw new BudgetGoalException("Invalid budget goal target amount. Please input correct transaction amount!");
        }
    }
}