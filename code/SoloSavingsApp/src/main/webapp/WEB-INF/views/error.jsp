<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>404 - Not Found</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
    <jsp:include page="templates/header.jsp" />
    <div class="container">
        <h1>404 - Not Found</h1>
        <p>The requested resource was not found.</p>
        <div class="back-link">
            <a href="/">Go back to the homepage</a>
        </div>
    </div>
    <jsp:include page="templates/footer.jsp" />
</body>
</html>