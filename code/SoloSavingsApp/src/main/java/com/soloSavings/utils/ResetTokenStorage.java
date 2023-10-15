package com.soloSavings.utils;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ResetTokenStorage {

    private final Map<String, String> tokenMap = new HashMap<>();

    public void storeToken(String userName, String token) {
        tokenMap.put(userName, token);
    }

    public String retrieveUsername(String token) {
        for (Map.Entry<String, String> entry : tokenMap.entrySet()) {
            if (entry.getValue().equals(token)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void removeTokenRecord(String userName) {
        tokenMap.remove(userName);
    }

    public void printTokenMap() {
        System.out.println("Token Map Contents:");
        for (Map.Entry<String, String> entry : tokenMap.entrySet()) {
            String username = entry.getKey();
            String token = entry.getValue();
            System.out.println("Username: " + username + ", Token: " + token);
        }
    }
}