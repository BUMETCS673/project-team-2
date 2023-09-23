package com.soloSavings.serviceImpl;

import com.soloSavings.model.Expense;
import com.soloSavings.repository.ExpenseRepository;
import com.soloSavings.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService{
    //@Autowired
    //UserRepository userRepository;

    @Autowired
    ExpenseRepository expenseRepository;

    @Override
    public String addExpense(Expense expense){
        //User user = userRepository.findBy(user_id);
        Expense newExpense = expenseRepository.save(expense);
        double newBalance = 100.0;
        return "Expense Added " + newExpense.getExpense_id() + ". New Balance " + Double.toString(newBalance);
    }

public List<Expense> getExpenses(){
        return expenseRepository.findAll();
}

public Optional<Expense> getExpense(Integer expense_id){
        return expenseRepository.findById(expense_id);
}

}
