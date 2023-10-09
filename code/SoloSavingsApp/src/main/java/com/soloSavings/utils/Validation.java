package com.soloSavings.utils;

import com.soloSavings.model.helper.TransactionType;

public class Validation {
    public static boolean validateTransaction(Double userBal, Double amount, TransactionType transType){
        if(transType.equals(TransactionType.CREDIT)){
            return amount > 0;
        } else {
            return amount > 0 && amount < userBal;
        }
    }

    public static boolean validateBudgetGoal(Double targetAmount){
        return targetAmount > 0;
    }
}
