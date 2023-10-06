<%--
  Created by IntelliJ IDEA.
  User: hanwenzhang
  Date: 10/5/23
  Time: 10:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.UUID" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Forget Password</title>
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
        p{
            text-align: center;
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
<h1>Forget Password</h1>
<p>Please enter your username below.</p>
<p>If we find your account, we will email you the instruction to reset the password.</p>
<form id="forgetForm" style="margin-top: 2rem">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username" required>
    <input type="submit" value="Submit">
</form>
</body>
<footer>
    &copy; 2023 SoloSavings. All rights reserved.
</footer>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    $(document).ready(function() {
        $('#forgetForm').submit(function(event) {
            event.preventDefault();
            const formData = {
                username: $('input[name="username"]').val(),
            };

            $.ajax({
                type: 'POST',
                url: '/api/forget-password',
                contentType: 'application/json',
                data: JSON.stringify(formData),
                success: function(response) {
                    console.log('User found, email sent:', response);
                    alert("User found, please check your email.");
                    window.location.replace("/solosavings/reset-password");
                },
                error: function(error) {
                    alert('Something went wrong, user is not found, please try again.');
                    $('#forgetForm')[0].reset();
                    console.error('User is not found:', error);
                }
            });
        });
    });
</script>
</html>
