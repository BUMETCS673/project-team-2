<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>SoloSavings - Your Financial Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f2f2f2;
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

        h1, h2, h3, p {
            text-align: center;
        }

        main {
            max-width: 800px;
            margin: 0 auto;
            padding: 2rem;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 1rem;
        }

        table, th, td {
            border: 1px solid #ccc;
        }

        th, td {
            padding: 0.5rem;
            text-align: left;
        }

        tfoot {
            font-weight: bold;
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

        /* Modal styles */
        .modal {
            display: none; /* Hidden by default */
            position: fixed; /* Stay in place */
            z-index: 1; /* Sit on top */
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0,0,0,0.4);
        }

        /* Close Button */
        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }

        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }

        button {
            padding: 10px 20px;
            background-color: #007acc;
            color: white;
            border: none;
            cursor: pointer;
            border-radius: 5px;
        }

        button:hover {
            background-color: #005fbb;
        }

        .buttons {
            background-color: #f2f2f2;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .add-expense-btn, .budget-goals-btn, #analytics-button {
            margin: 10px;
        }

        /* Modal Content/Box */
        .modal-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 20%;
        }

        .form-group, .add-button {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin-bottom: 20px;
        }

        #add-income-form {
            text-align: center;
        }
    </style>
</head>
<body>
<header>
    <nav>
        <ul>
            <li><a href="/solosavings" style="color: white;">Home</a></li>
            <li id="login"><a href="/solosavings/login" style="color: white;">Login</a></li>
            <li id="logout"><a onclick="logout()">Logout</a></li>
            <li><a href="/solosavings/register" style="color: white;">Register</a></li>
        </ul>
    </nav>
</header>
<h1>Explore SoloSavings Features</h1>
<main>
    <!-- Add your content about SoloSavings features here -->
    <section class="feature">
        <h2>Comprehensive Analytics</h2>
        <p>Gain insights into your financial transactions with detailed analytics and charts.</p>
    </section>

    <section class="feature">
        <h2>Budget Goals</h2>
        <p>Set and track your budget goals to achieve your financial aspirations.</p>
    </section>

    <section class="feature">
        <h2>Expense Management</h2>
        <p>Efficiently record your expenses and categorize them for better control.</p>
    </section>
    
  <div class="registration-cta" style="text-align: center;">
    <h2>Ready to Take Control of Your Finances?</h2>
    <p>If you're interested in managing your finances effectively, start your financial journey with SoloSavings today!</p>
    <button><a href="/solosavings/register" style="text-decoration: none; color: white;">Register Now</a></button>
</div>

</main>
<footer>
    &copy; 2023 SoloSavings
</footer>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</body>
</html>
