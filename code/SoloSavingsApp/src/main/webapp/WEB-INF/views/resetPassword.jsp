<%--
  Created by IntelliJ IDEA.
  User: hanwenzhang
  Date: 10/5/23
  Time: 10:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.UUID" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Reset Password</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 0;
        }
        header {
            background-color: #333;
            color: white;
            padding: 10px;
        }
        nav ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
        }
        nav ul li {
            display: inline;
            margin-right: 20px;
            color: white;
        }
        h1 {
            text-align: center;
            margin-top: 50px;
        }

        form {
            background-color: white;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
            padding: 20px;
            max-width: 400px;
            margin: 0 auto;
            display: flex;
            justify-content: flex-start;
            align-items: center;
            flex-direction: column;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"],
        input[type="email"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-top: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        input[type="submit"] {
            background-color: #007bff;
            color: #fff;
            padding: 10px 20px;
            margin-top: 10px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }

        footer {
            background-color: #333;
            color: white;
            text-align: center;
            padding: 10px;
            position: absolute;
            bottom: 0;
            width: 100%;
        }
    </style>
</head>
<body>
<header>
    <nav>
        <ul>
            <li><a href="/solosavings" style="color: white;">Home</a></li>
            <li><a href="/solosavings/login" style="color: white;">Login</a></li>
            <li><a href="/solosavings/register" style="color: white;">Register</a></li>
        </ul>
    </nav>
</header>
<h1>Reset Password</h1>
<form id="resetForm">
    <input type="hidden" name="token" value="<%= request.getParameter("token") %>">
    <!-- You should include a hidden field for the reset token -->

    <label for="reset_token">Reset Token:</label>
    <input type="text" id="reset_token" name="reset_token" required>
    <br>

    <label for="password">New Password:</label>
    <input type="password" id="password" name="password" required>
    <br>

    <label for="confirm_password">Confirm Password:</label>
    <input type="password" id="confirm_password" name="confirm_password" required>
    <br>

    <input type="submit" value="Reset Password">
</form>
<%
    if (request.getMethod().equals("POST")) {
        // Retrieve the username or email submitted in the form
        String username = request.getParameter("username");

        // Check if the username or email is valid and exists in your database
        // You should implement this logic according to your database schema

        if (isValid(username)) {
            // Generate a temporary reset token (you can use a library for this)
            String resetToken = UUID.randomUUID().toString();

            // Send an email with the reset link containing the resetToken
            // You should implement this part using JavaMail or another email library

            // For demonstration purposes, we'll print the resetToken here
            %>
                <p>A password reset link has been sent to your email.</p>
                <p>Please check your inbox (and spam folder) for instructions.</p>
                <p>Reset Token: <%= resetToken %></p>
            <%
            } else {
                // Handle the case where the username or email is not valid
            %>
                <p>Invalid username. Please try again.</p>
            <%
        }
    }
%>
<%!
    private boolean isValid(String username) {
        if (username == null) return false;
        return false;
    }
%>
</body>
<footer>
    &copy; 2023 SoloSavings. All rights reserved.
</footer>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    function isValidationCheckPassed() {
        const password = document.getElementById("password").value;
        const confirmPassword = document.getElementById("confirm_password").value;
        const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;

        if (!passwordPattern.test(password)) {
            alert("Password must contain at least one uppercase letter, one lowercase letter, one digit, and be at least 8 characters long.");
            return false;
        }

        if (password !== confirmPassword) {
            alert("Passwords do not match.");
            return false;
        }

        return true;
    }

    $(document).ready(function() {
        $('#resetForm').submit(function(event) {
            event.preventDefault();
            if(!isValidationCheckPassed()) return;
            const formData = {
                reset_token: $('input[name="reset_token"]').val(),
                password: $('input[name="password"]').val(),
            };

            $.ajax({
                type: 'POST',
                url: '/api/reset-password',
                contentType: 'application/json',
                data: JSON.stringify(formData),
                success: function(response) {
                    console.log('Reset password successfully:', response);
                    alert("Password reset successfully, redirect to login page.");
                    window.location.replace("/solosavings/login");
                },
                error: function(error) {
                    alert(error.responseText);
                    console.error('Reset password failed:', error);
                }
            });
        });
    });
</script>
</html>
