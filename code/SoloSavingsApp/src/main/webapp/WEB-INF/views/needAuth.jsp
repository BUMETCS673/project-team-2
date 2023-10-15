<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Need Authentication</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css">
 </head>
<body>
    <jsp:include page="templates/header.jsp" />
    <div class="container">
        <h1>You are not authenticated</h1>
        <p>You need to log in to your account in order to view the page.</p>
        <div class="back-link">
            <a href="/">Go back to the homepage</a>
        </div>
    </div>
    <jsp:include page="templates/footer.jsp" />
</body>
</html>