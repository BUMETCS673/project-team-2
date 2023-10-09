package com.soloSavings.service;

import com.soloSavings.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

/*
 * Copyright (c) 2023 Team 2 - SoloSavings
 * Boston University MET CS 673 - Software Engineering
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Team 2 - SoloSavings Application
 */

public interface UserService extends UserDetailsService {
    public void save(User user);
    public Double getBalance(Integer id);
    public String getPasswordHash(String email);
    public User getUserByName(String username);
    public User getUserByEmail(String email);
    public void setUserNewPassword(String username, String newPassword);
}
