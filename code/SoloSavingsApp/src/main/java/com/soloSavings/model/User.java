package com.soloSavings.model;

import com.soloSavings.config.SecurityConfig;
import jakarta.persistence.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    private String username;
    @Column(unique = true)
    private String email;
    private String password_hash;
    private LocalDate registration_date;
    private Double balance_amount;
    private LocalDate last_updated;

    public User(String username, String email, String plain_text_password, Double balance_amount) throws NoSuchAlgorithmException {
        this.username = username;
        this.email = email;
        this.password_hash = SecurityConfig.hashedPassword(plain_text_password);
        this.balance_amount = balance_amount;
    }
    public Integer getId() {
        return id;
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
    public boolean isPasswordMatches(String password_hash) {
        return true;
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
