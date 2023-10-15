package com.soloSavings.service;

import com.soloSavings.exceptions.BudgetGoalException;
import com.soloSavings.model.BudgetGoalTracker;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BudgetGoalTrackerService {

    List<BudgetGoalTracker> findAllGoalsByUserId(Integer userId);

}
