package com.soloSavings.serviceImpl;

import com.soloSavings.model.BudgetGoalTracker;
import com.soloSavings.repository.BudgetGoalTrackerRepository;
import com.soloSavings.service.BudgetGoalService;
import com.soloSavings.service.BudgetGoalTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetGoalTrackerServiceImpl implements BudgetGoalTrackerService {

    private final BudgetGoalTrackerRepository budgetGoalTrackerRepository;

    @Autowired
    public BudgetGoalTrackerServiceImpl(BudgetGoalTrackerRepository budgetGoalTrackerRepository) {
        this.budgetGoalTrackerRepository = budgetGoalTrackerRepository;
    }

    @Override
    public List<BudgetGoalTracker> findAllGoalsByUserId(Integer userId){
        return budgetGoalTrackerRepository.findAllGoalsByUserId(userId);
    }
}
