<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <title>SoloSavings Analytics</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
    <jsp:include page="templates/header.jsp" />
    <div>
        <h1>View your monthly analytics this year</h1>
    </div>
    <main style="max-width: 60% !important;">
        <div id="chartContainer" style="height: 400px; width: 100%;"></div>
        <script src="https://cdn.canvasjs.com/canvasjs.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="/js/solosavingsAnalytics.js"></script>
    </main>
    <jsp:include page="templates/footer.jsp" />
</body>
</html>