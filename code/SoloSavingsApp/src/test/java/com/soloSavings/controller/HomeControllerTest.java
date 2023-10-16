package com.soloSavings.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class HomeControllerTest {

    @Autowired
    HomeController homeController;
    @Test
    public void testErrorPage(){
        String expected = "redirect:/solosavings";
        String actual = homeController.home();
        assertEquals(expected,actual);
    }

    @Test
    public void testWelcome(){
        String expected = "home";
        String actual = homeController.welcome();
        assertEquals(expected,actual);
    }

    @Test
    public void testLogin(){
        String expected = "login";
        String actual = homeController.login();
        assertEquals(expected,actual);
    }

    @Test
    public void testRegister(){
        String expected = "register";
        String actual = homeController.register();
        assertEquals(expected,actual);
    }

    @Test
    public void testForgetPassword(){
        String expected = "forgetPassword";
        String actual = homeController.forgetPassword();
        assertEquals(expected,actual);
    }

    @Test
    public void testResetPassword(){
        String expected = "resetPassword";
        String actual = homeController.resetPassword();
        assertEquals(expected,actual);
    }

    @Test
    public void testAnalytics(){
        String expected = "analytics";
        String actual = homeController.analytics();
        assertEquals(expected,actual);
    }

    @Test
    public void testTransactionHistory(){
        String expected = "transactionHistory";
        String actual = homeController.transactionHistory();
        assertEquals(expected,actual);
    }

    @Test
    public void testBudgetGoals(){
        String expected = "BudgetGoals";
        String actual = homeController.budgetGoals();
        assertEquals(expected,actual);
    }

    @Test
    public void testDashboard(){
        String expected = "dashboard";
        String actual = homeController.dashboard();
        assertEquals(expected,actual);
    }

    @Test
    public void testDetails(){
        String expected = "details";
        String actual = homeController.details();
        assertEquals(expected,actual);
    }
}
