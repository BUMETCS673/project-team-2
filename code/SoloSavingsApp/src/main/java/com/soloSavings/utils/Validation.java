package com.soloSavings.utils;

public class Validation {
    public static boolean validateIncome(Double income){
        return income > 0;
    }

    public static boolean validateExpense(Double expense){
        return expense > 0;
    }
}
