package com.soloSavings.model;

import jakarta.persistence.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
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
