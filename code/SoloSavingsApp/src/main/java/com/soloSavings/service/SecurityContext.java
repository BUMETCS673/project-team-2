package com.soloSavings.service;

import com.soloSavings.model.User;

public interface SecurityContext {
    public User getCurrentUser();
    public void setContext(org.springframework.security.core.context.SecurityContext context);
    public void dispose();
}
