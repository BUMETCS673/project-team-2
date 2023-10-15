package com.soloSavings.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CustomErrorControllerTest {

    @Autowired
    CustomErrorController customErrorController;
    @Test
    public void testErrorPage(){
        String expected = "error";
        String actual = customErrorController.handleError();
        assertEquals(expected,actual);
    }
}
