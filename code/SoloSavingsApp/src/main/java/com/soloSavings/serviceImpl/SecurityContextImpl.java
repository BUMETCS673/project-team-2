package com.soloSavings.serviceImpl;

import com.soloSavings.config.JwtUtil;
import com.soloSavings.model.User;
import com.soloSavings.service.SecurityContext;
import com.soloSavings.service.UserService;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityContextImpl implements SecurityContext {
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    UserService userService;
    static User currentUser;

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void setContext(org.springframework.security.core.context.SecurityContext context) {
        var username = context.getAuthentication().getName();
        currentUser = userService.getUserByName(username);
    }

    @Override
    public void dispose() {
        currentUser = null;
    }


}
