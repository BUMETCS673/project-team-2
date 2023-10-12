package com.soloSavings.repository;

import com.soloSavings.model.BudgetGoalTracker;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetGoalTrackerRepository {

    @Query("SELECT t FROM BudgetGoalTracker t WHERE t.user_id = ?1")
    List<BudgetGoalTracker> findAllGoalsByUserId(Integer userId);

}
