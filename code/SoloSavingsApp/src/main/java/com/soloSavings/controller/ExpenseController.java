package com.soloSavings.controller;

import com.soloSavings.model.Expense;
import com.soloSavings.serviceImpl.ExpenseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="api/v1/expense")
public class ExpenseController {

    @Autowired
    ExpenseServiceImpl expenseServiceImpl;
    @PostMapping("add/{user_id}")
    public ResponseEntity<String> addExpense(@PathVariable("user_id") Integer user_id, @RequestBody Expense expense)
    {
        //Get user
        //Get current balance
        //Check if expense > balance - if so throw error - business rule expense cant be more than balance
        //Add Expense
        //Update user balance
        //Update user
        return new ResponseEntity<>(expenseServiceImpl.addExpense(expense), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Expense>> getExpenses(){
        return new ResponseEntity<>(expenseServiceImpl.getExpenses(), HttpStatus.OK);
    }

    @GetMapping("/{expense_id}")
    public ResponseEntity<Expense> getExpense(@PathVariable("expense_id") Integer expense_id) throws Exception {
       Optional<Expense> expense= expenseServiceImpl.getExpense(expense_id);
       if (expense.isPresent()){
           return new ResponseEntity<>(expense.get(), HttpStatus.OK);
       } else {
           return new ResponseEntity<>(new Expense(), HttpStatus.BAD_REQUEST);
       }
    }
}
