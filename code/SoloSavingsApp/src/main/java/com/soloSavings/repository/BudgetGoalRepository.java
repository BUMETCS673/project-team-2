package com.soloSavings.repository;

import com.soloSavings.model.BudgetGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetGoalRepository extends JpaRepository<BudgetGoal, Integer> {

    @Query("SELECT b FROM BudgetGoal b WHERE b.userId = ?1 AND MONTH(b.startDate) = MONTH(CURRENT_DATE()) AND YEAR(b.startDate) = YEAR(CURRENT_DATE())")
    List<BudgetGoal> findAllByUserIdCurrentMonth(Integer userId);
}
