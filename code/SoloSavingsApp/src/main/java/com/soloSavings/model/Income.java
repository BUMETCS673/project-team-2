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
    private Integer income_id;
    private Integer user_id;
    private String source;
    private Double amount;
    private LocalDate income_date;

    public Income(Integer income_id, Integer user_id, String source, Double amount, LocalDate income_date) {

        this.income_id = income_id;
        this.user_id = user_id;
        this.source = source;
        this.amount = amount;
        this.income_date = income_date;
    }

    public Income() {

    }

    public Integer getIncome_id() {
        return income_id;
    }

    public void setIncome_id(Integer income_id) {
        this.income_id = income_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getIncome_date() {
        return income_date;
    }

    public void setIncome_date(LocalDate income_date) {
        this.income_date = income_date;
    }

}

