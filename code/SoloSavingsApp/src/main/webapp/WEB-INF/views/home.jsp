<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SoloSavings - Home</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f0f0f0; /* Background color for the entire page */
        }

        header {
            background-color: #333;
            color: white;
            padding: 10px;
            text-align: center;
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

        .hero {
            text-align: center;
            padding: 80px 0;
        }

        .hero h1 {
            font-size: 36px;
            color: #333;
        }

        .hero p {
            font-size: 18px;
            color: #666;
            margin-top: 20px;
        }

        .btn-primary {
            display: inline-block;
            padding: 10px 30px;
            background-color: #007acc;
            color: white;
            border: none;
            cursor: pointer;
            font-size: 18px;
            text-decoration: none;
            margin-top: 20px;
        }

        .btn-primary:hover {
            background-color: #0052a3; /* Change color on hover */
        }

        .features {
            display: flex;
            justify-content: center;
            align-items: flex-start;
            flex-wrap: wrap;
            margin: 40px 0;
        }

        .feature {
            flex: 1;
            text-align: center;
            margin: 20px;
            padding: 20px;
            background-color: white;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
        }

        .feature h2 {
            font-size: 24px;
            margin-bottom: 10px;
            color: #333;
        }

        .feature p {
            font-size: 16px;
            color: #666;
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

    <footer>
        &copy; 2023 SoloSavings. All rights reserved.
    </footer>
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
