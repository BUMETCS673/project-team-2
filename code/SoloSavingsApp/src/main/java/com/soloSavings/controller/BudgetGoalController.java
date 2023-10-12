package com.soloSavings.controller;

import com.soloSavings.exceptions.BudgetGoalException;
import com.soloSavings.model.BudgetGoal;
import com.soloSavings.model.BudgetGoalTracker;
import com.soloSavings.service.BudgetGoalService;
import com.soloSavings.service.BudgetGoalTrackerService;
import com.soloSavings.service.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/budgetgoal")
public class BudgetGoalController {

    @Autowired
    BudgetGoalService budgetGoalServiceImpl;

    @Autowired
    BudgetGoalTrackerService budgetGoalTrackerService;

    @Autowired
    SecurityContext securityContext;

    @RequestMapping(value="add", method=RequestMethod.POST)
    public ResponseEntity<?> addBudgetGoal (@RequestBody BudgetGoal budgetGoal){
        securityContext.setContext(SecurityContextHolder.getContext());
        try{
            BudgetGoal budgetGoalReturned = budgetGoalServiceImpl.addBudgetGoal(securityContext.getCurrentUser().getUser_id(), budgetGoal);
            securityContext.dispose();
            return new ResponseEntity<>(budgetGoalReturned,HttpStatus.OK);
        } catch (BudgetGoalException e) {
            securityContext.dispose();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("all")
    public ResponseEntity<?> getBudgetGoals() {
        securityContext.setContext(SecurityContextHolder.getContext());
        List<BudgetGoalTracker> budgetGoalTrackerList = budgetGoalTrackerService.findAllGoalsByUserId(securityContext.getCurrentUser().getUser_id());
        securityContext.dispose();
        return new ResponseEntity<>(budgetGoalTrackerList,HttpStatus.OK);
    }

    @DeleteMapping("delete/{budgetgoal_id}")
    public ResponseEntity<?> deleteBudgetGoal(@PathVariable("budgetgoal_id") Integer budgetgoal_id){
        securityContext.setContext(SecurityContextHolder.getContext());
        try {
            BudgetGoal budgetGoalReturned = budgetGoalServiceImpl.deleteBudgetGoal(budgetgoal_id);
            return new ResponseEntity<>(budgetGoalReturned , HttpStatus.OK);
        } catch (BudgetGoalException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
