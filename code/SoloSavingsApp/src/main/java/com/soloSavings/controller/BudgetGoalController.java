package com.soloSavings.controller;

import com.soloSavings.exceptions.BudgetGoalException;
import com.soloSavings.model.BudgetGoal;
import com.soloSavings.service.BudgetGoalService;
import com.soloSavings.service.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/budgetgoal")
public class BudgetGoalController {

    @Autowired
    BudgetGoalService budgetGoalServiceImpl;

    @Autowired
    SecurityContext securityContext;

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public ResponseEntity<?> addBudgetGoal (@RequestBody BudgetGoal budgetGoal){
        securityContext.setContext(SecurityContextHolder.getContext());
        try{
            budgetGoalServiceImpl.addBudgetGoal(securityContext.getCurrentUser().getUser_id(), budgetGoal);
            securityContext.dispose();
            return new ResponseEntity<>("Budget goal added.",HttpStatus.OK);
        } catch (BudgetGoalException e) {
            securityContext.dispose();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}
