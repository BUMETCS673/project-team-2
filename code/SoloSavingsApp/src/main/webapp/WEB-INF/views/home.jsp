<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SoloSavings - Home</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css">
 </head>
<body>
    <jsp:include page="templates/header.jsp" />

    <div class="hero">
        <h1>Welcome to SoloSavings</h1>
        <p>Your Money Management Solution</p>
        <a href="#" class="btn-primary" id="get-started-button">Get Started</a>
    </div>

    <section class="features">
        <div class="feature">
            <h2>Track Expenses</h2>
            <p>Effortlessly manage your expenses and see where your money goes.</p>
        </div>
        <div class="feature">
            <h2>Set Savings Goals</h2>
            <p>Define financial goals and start saving towards a better future.</p>
        </div>
        <div class="feature">
            <h2>Visualize Finances</h2>
            <p>Get insights with beautiful charts and graphs for your financial data.</p>
        </div>
    </section>

    <jsp:include page="templates/footer.jsp" />
</body>
</html>
 <script>
        document.getElementById("get-started-button").addEventListener("click", function(event) {
            event.preventDefault();

            const jwtToken = document.cookie.replace(/(?:(?:^|.*;\s*)jwtToken\s*\=\s*([^;]*).*$)|^.*$/, "$1");
            if (jwtToken) {
                // Redirect to the dashboard page if the user has a JWT token
                window.location.href = "/solosavings/dashboard";
            } else {
                // Redirect to the details page if the user doesn't have a JWT token
                window.location.href = "/solosavings/details";
            }
        });
    </script>
