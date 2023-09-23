package com.soloSavings.service;

import com.soloSavings.model.Expense;

import java.util.List;
import java.util.Optional;

public interface ExpenseService {

    public String addExpense(Expense expense);

    public List<Expense> getExpenses();
    public Optional<Expense> getExpense(Integer expense_id);


}
