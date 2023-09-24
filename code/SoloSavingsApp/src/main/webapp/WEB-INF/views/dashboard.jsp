<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>SoloSavings Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
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

        .sub-main{
            background-color: #f2f2f2;
            display: flex;
            flex-direction: row;
        }

        .leftcol,
        .midcol,
        .rightcol {
            flex: 1;
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
    <h1>SoloSavings Dashboard</h1>
    <main>
        <h1>Welcome to your SoloSavings Dashboard</h1>
        <div class="sub-main">
            <div class="leftcol">
                <h2>Money Earned</h2>
                <p>Your total income for this month:</p>
                <p id="income-val"></p>
                <h3>Income resources</h3>
            </div>
            <div class="midcol">
                <h2>Remaining Balance</h2>
                <p>Your current balance:</p>
                <p  id="total-balance">{balance}</p>
            </div>
            <div class="rightcol">
                <h2>Expenses</h2>
                <p>Your total expense for this month:</p>
                <p id="expense-val"></p>
                <h3>Expense details</h3>
            </div>
        </div>
    </main>
    <footer>
        &copy; 2023 SoloSavings
    </footer>
</body>
<footer>
    &copy; 2023 SoloSavings
</footer>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    let thisMonthIncome;
    let thisMonthExpense;
    let totalBalance;
    $.ajax({
        async: false,
        type: 'GET',
        url: '/transaction/monthly/income/1', // change user_id later
        contentType: 'application/json',
        success: function(response) {
            thisMonthIncome = response;
            $('#income-val').text("$"+thisMonthIncome);
        },
        error: function(error) {
            console.error('Something went wrong!', error);
        }
    });

    $.ajax({
        async: false,
        type: 'GET',
        url: '/transaction/monthly/expense/1', // change user_id later
        contentType: 'application/json',
        success: function(response) {
            thisMonthExpense = response;
            $('#expense-val').text("$"+thisMonthExpense);
        },
        error: function(error) {
            console.error('Something went wrong!', error);
        }
    });

    $.ajax({
        async: false,
        type: 'GET',
        url: '/user/balance/1', // change user_id later
        contentType: 'application/json',
        success: function(response) {
            totalBalance = response;
            $('#total-balance').text("$"+totalBalance);
        },
        error: function(error) {
            console.error('Something went wrong!', error);
        }
    });
</script>
</html>