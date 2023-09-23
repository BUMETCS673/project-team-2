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
@Table(name = "income")
public class Income {

    @Id
    @GeneratedValue
    private int income_id;
    private int user_id;
    private String source;
    private Long amount;
    private LocalDate income_date;

    public Income(int income_id, int user_id, String source, Long amount, LocalDate income_date) {

        this.income_id = income_id;
        this.user_id = user_id;
        this.source = source;
        this.amount = amount;
        this.income_date = income_date;
    }

    public Income() {

    }

    public int getIncome_id() {
        return income_id;
    }

    public void setIncome_id(int income_id) {
        this.income_id = income_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public LocalDate getIncome_date() {
        return income_date;
    }

    public void setIncome_date(LocalDate income_date) {
        this.income_date = income_date;
    }

}

