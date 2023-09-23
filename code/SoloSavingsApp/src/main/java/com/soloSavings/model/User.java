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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Integer user_id ;
    private String username;
    private String email;
    private String password_hash;
    private LocalDate registration_date;
    private Double balance_amount;
    private LocalDate last_updated;


    public User(Integer user_id, String username, String email, String password_hash, LocalDate registration_date,
                Double balance_amount, LocalDate last_updated) {
        this.user_id = user_id;
        this.username = username;
        this.email = email;
        this.password_hash = password_hash;
        this.registration_date = registration_date;
        this.balance_amount = balance_amount;
        this.last_updated = last_updated;
    }

    public User() {

    }

    public Integer getUser_id() {
        return user_id;
    }
    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword_hash() {
        return password_hash;
    }
    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }
    public LocalDate getRegistration_date() {
        return registration_date;
    }
    public void setRegistration_date(LocalDate registration_date) {
        this.registration_date = registration_date;
    }
    public Double getBalance_amount() {
        return balance_amount;
    }
    public void setBalance_amount(Double balance_amount) {
        this.balance_amount = balance_amount;
    }
    public LocalDate getLast_updated() {
        return last_updated;
    }
    public void setLast_updated(LocalDate last_updated) {
        this.last_updated = last_updated;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return getUsername() + "(" + getEmail() + ")";
    }

}
