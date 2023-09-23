package com.soloSavings.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Integer user_id ;
    private String username;
    private String email;
    private byte[] password_hash;
    private LocalDate registration_date;
    private Double balance_amount;
    private LocalDate last_updated;


    public User(String username, String email, String password_hash, Double balance_amount) throws NoSuchAlgorithmException {
        MessageDigest digestor = MessageDigest.getInstance("SHA-256");
        this.username = username;
        this.email = email;
        this.password_hash = digestor.digest(password_hash.getBytes(StandardCharsets.UTF_8));
        this.balance_amount = balance_amount;
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
    public byte[] getPassword_hash() {
        return password_hash;
    }
    public void setPassword_hash(String password_hash) throws NoSuchAlgorithmException {
        MessageDigest digestor = MessageDigest.getInstance("SHA-256");
        this.password_hash = digestor.digest(password_hash.getBytes(StandardCharsets.UTF_8));
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
