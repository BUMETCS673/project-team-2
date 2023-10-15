package com.soloSavings.controller;

import com.soloSavings.serviceImpl.SecurityContextImpl;
import com.soloSavings.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    UserServiceImpl userServiceImpl;
    @Autowired
    SecurityContextImpl securityContext;

    @RequestMapping(value = "balance", method = RequestMethod.GET)
    public ResponseEntity<?> getTotalBalance (){
        securityContext.setContext(SecurityContextHolder.getContext());
        Double balance = userServiceImpl.getBalance(securityContext.getCurrentUser().getUser_id());
        securityContext.dispose();
        return new ResponseEntity<>(balance, HttpStatus.OK);
    }
}
