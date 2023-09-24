package com.soloSavings.service;

import com.soloSavings.model.User;
import com.soloSavings.repository.UserRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

/*
 * Copyright (c) 2023 Team 2 - SoloSavings
 * Boston University MET CS 673 - Software Engineering
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Team 2 - SoloSavings Application
 */

public interface UserService {
    public User save(User user);
    public Double getBalance(Integer id);
    public String getPasswordHash(String email);
    public User getUserByName(String username);
    public User getUserByEmail(String email);
}
