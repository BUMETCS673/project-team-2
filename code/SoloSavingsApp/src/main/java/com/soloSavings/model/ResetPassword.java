package com.soloSavings.model;

public record ResetPassword(String token, String username, String password) {
}
