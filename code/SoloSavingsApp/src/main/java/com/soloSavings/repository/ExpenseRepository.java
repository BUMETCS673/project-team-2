package com.soloSavings.repository;

import com.soloSavings.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
 * Copyright (c) 2023 Team 2 - SoloSavings
 * Boston University MET CS 673 - Software Engineering
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Team 2 - SoloSavings Application
 */
@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Integer> {





    //resources for database layer (spring-data)
    //Put

//    public addExpense
// return repository.save(product);
// return repository.saveAll(products);
//
////Get
//return repository.findAll();
// return repository.findById(id).orElse(null);
//return repository.findByName(name);  //Create Method in Repository
//
////Delete
// repository.deleteById(id);



}
