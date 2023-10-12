package com.soloSavings.repository;

import com.soloSavings.model.BudgetGoalTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetGoalTrackerRepository extends JpaRepository<BudgetGoalTracker, Integer> {

    @Query("SELECT t FROM BudgetGoalTracker t WHERE t.userId = ?1")
    List<BudgetGoalTracker> findAllGoalsByUserId(Integer userId);

}
