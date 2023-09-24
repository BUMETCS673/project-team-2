package com.soloSavings.controller;

import com.soloSavings.exceptions.TransactionException;
import com.soloSavings.model.Transaction;
import com.soloSavings.service.TransactionService;
import com.soloSavings.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @RequestMapping(value = "balance/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getTotalBalance (@PathVariable("id") Integer id){
//        try{
            Double balance = userServiceImpl.getBalance(id);
            return new ResponseEntity<>(balance, HttpStatus.OK);
//        }
//        catch (TransactionException e){
//            return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY); // 422 code for invalid requestbody for transaction
//        }
    }
}
