package com.soloSavings.repository;

import com.soloSavings.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/*
 * Copyright (c) 2023 Team 2 - SoloSavings
 * Boston University MET CS 673 - Software Engineering
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Team 2 - SoloSavings Application
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    @Query("SELECT e FROM User e WHERE e.username = ?1")
    User findByUsername(String username);
    @Query("SELECT e.password_hash FROM User e where e.email = ?1")
    String findPasswordHashByEmail(String email);

    @Query("SELECT e FROM User e WHERE e.email = ?1")
    User findUserByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.password_hash = ?1 WHERE u.username = ?2")
    void updatePasswordByUsername(String password_hash, String username);
}
