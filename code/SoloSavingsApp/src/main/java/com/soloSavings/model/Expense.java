package com.soloSavings.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

/*
 * Copyright (c) 2023 Team 2 - SoloSavings
 * Boston University MET CS 673 - Software Engineering
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Team 2 - SoloSavings Application
 */
@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue
    private Integer expense_id;
    private Integer user_id;
    private String category;
    private Double amount;
    private LocalDate expense_date;

    public Expense(Integer expense_id, Integer user_id, String category, Double amount, LocalDate expense_date) {
        this.expense_id = expense_id;
        this.user_id = user_id;
        this.category = category;
        this.amount = amount;
        this.expense_date = expense_date;
    }

    public Expense() {

    }

    public Integer getExpense_id() {
        return expense_id;
    }

    public void setExpense_id(Integer expense_id) {
        this.expense_id = expense_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getExpense_date() {
        return expense_date;
    }

    public void setExpense_date(LocalDate expense_date) {
        this.expense_date = expense_date;
    }

}

