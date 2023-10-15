<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SoloSavings - Register</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css">
    <style>
       .container {
            text-align: center !important;
            margin: 50px auto !important;
            max-width: 400px !important;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
        }

        h1 {
            margin-top: 0 !important;
        }

        p {
            margin-top: 20px;
            font-size: 14px;
        }


    </style>
</head>
<body>
<jsp:include page="templates/header.jsp" />

    <div class="container">
        <h1>Register for SoloSavings</h1>
        <form id="registrationForm">
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required>
            </div>

            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
            </div>

            <div class="form-group">
                <label for="password_hash">Password:</label>
                <input type="password" id="password_hash" name="password_hash" required>
            </div>

            <div class="form-group">
                <label for="confirm_password">Confirm Password:</label>
                <input type="password" id="confirm_password" name="confirm_password" required>
            </div>

            <button type="submit">Register</button>
        </form>
        <p>Already have an account? <a href="/solosavings/login">Login here</a></p>
    </div>
    <jsp:include page="templates/footer.jsp" />
</body>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="/js/solosavingsRegister.js"></script>
</html>
