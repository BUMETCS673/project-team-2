<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SoloSavings - Login</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
    <jsp:include page="templates/header.jsp" />

    <h1 style="margin-top: 50px;">Login to SoloSavings</h1>

    <section>
        <form id="loginForm">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" required>
            
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" required>
            
            <button type="submit">Login</button>
        </form>
    </section>

    <section>
        <p>Don't have an account? <a href="/solosavings/register">Register here</a></p>
        <p>Forget your password? <a href="/solosavings/forget-password">Click here</a></p>
    </section>
    <jsp:include page="templates/footer.jsp" />
</body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="/js/solosavingsLogin.js"></script>
</html>
