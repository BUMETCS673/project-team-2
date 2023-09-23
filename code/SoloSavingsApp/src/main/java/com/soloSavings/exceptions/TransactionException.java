package com.soloSavings.exceptions;
/*
 * Copyright (c) 2023 Team 2 - SoloSavings
 * Boston University MET CS 673 - Software Engineering
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Team 2 - SoloSavings Application
 */
public class TransactionException extends Exception{
    public TransactionException(String message){
        super(message);
    }
}
