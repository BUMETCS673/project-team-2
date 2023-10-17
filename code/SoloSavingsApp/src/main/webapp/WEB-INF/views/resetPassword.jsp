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
    <link href="/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<jsp:include page="templates/header.jsp" />
<h1>Reset Password</h1>
<form id="resetForm">
    <label for="token">Reset Token:</label>
    <input type="text" id="token" name="token" required>
    <br>

    <label for="username">Username:</label>
    <input type="text" id="username" name="username" required>
    <br>

    <label for="password">New Password:</label>
    <input type="password" id="password" name="password" required>
    <br>

    <label for="confirm_password">Confirm Password:</label>
    <input type="password" id="confirm_password" name="confirm_password" required>
    <br>

    <input type="submit" value="Reset Password">
</form>
<jsp:include page="templates/footer.jsp" />
</body>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="/js/solosavingsResetPassword.js"></script>
</html>
