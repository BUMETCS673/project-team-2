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
    <link href="/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
    <jsp:include page="templates/header.jsp" />
    <h1 style="margin-top: 50px;">Forget Password</h1>
    <p>Please enter your username below.</p>
    <p>If we find your account, we will email you the instruction to reset the password.</p>
    <form id="forgetForm" style="margin-top: 2rem">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>
        <input type="submit" value="Submit">
    </form>
    <jsp:include page="templates/footer.jsp" />
</body>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="/js/solosavingsForgetPassword.js"></script>
</html>
