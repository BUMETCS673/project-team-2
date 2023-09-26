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

import lombok.*;

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

@Getter
@Setter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_id ;
    private String username;
    @Column(unique = true)
    private String email;
    private String password_hash;
    private LocalDate registration_date;
    private Double balance_amount;
    private LocalDate last_updated;

    public User() {
        this.balance_amount = 0.00;
        this.registration_date = LocalDate.now();
        this.last_updated = LocalDate.now();
    }
}
