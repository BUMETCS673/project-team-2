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

        /* Modal Content/Box */
        .modal-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
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
        }    </style>
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
        <!-- Add Income Button -->
        <button id="add-income-btn">Add Income</button>
        <!-- Modal -->
        <div id="add-income-modal" class="modal">

            <!-- Modal content -->
            <div class="modal-content">
                <span class="close">&times;</span>
                <h2>Add Income</h2>
                <form>
                    <label for="income-source">Source:</label>
                    <input type="text" id="income-source">

                    <label for="income-amount">Amount:</label>
                    <input type="number" id="income-amount">

                    <button type="submit">Submit</button>
                </form>
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
    let jwt = "";
    function setAuthHeader() {
        const jwtToken = document.cookie.replace(/(?:(?:^|.*;\s*)jwtToken\s*\=\s*([^;]*).*$)|^.*$/, "$1");
        if(jwtToken) {
            jwt = jwtToken;
            console.log("Found JWT cookie: " + jwt);

        } else {
            console.log("Failed to find JWT cookie");
            alert("You must login before accessing the dashboard");
            window.location.replace("/solosavings/login");
        }
    }
    setAuthHeader();

    let thisMonthIncome;
    let thisMonthExpense;
    let totalBalance;
    $.ajaxSetup({
        headers: {
            'Authorization': "Bearer " + jwt
        }
    })
    $.ajax({
        async: false,
        type: 'GET',
        url: '/api/transaction/monthly/income',
        contentType: 'application/json',
        success: function(response) {
            thisMonthIncome = response;
        },
        error: function(error) {
            console.error('Something went wrong!', error);
        }
    });
    $.ajax({
        async: false,
        type: 'GET',
        url: '/api/transaction/monthly/expense',
        contentType: 'application/json',
        success: function(response) {
            thisMonthExpense = response;

        },
        error: function(error) {
            console.error('Something went wrong!', error);
        }
    });
    $.ajax({
        async: false,
        type: 'GET',
        url: '/api/user/balance',
        contentType: 'application/json',
        success: function(response) {
            totalBalance = response;
        },
        error: function(error) {
            console.error('Something went wrong!', error);
        }
    });

    // Get modal element
    var modal = document.getElementById("add-income-modal");

    // Get "Add Income" button
    var addIncomeBtn = document.getElementById("add-income-btn");

    // When "Add Income" button clicked...
    $("#add-income-btn").click(function() {

        // Show the modal
        modal.style.display = "block";

    });

    // When user clicks "x" to close modal
    var closeModal = document.getElementsByClassName("close")[0];

    closeModal.onclick = function() {

        // Hide the modal
        modal.style.display = "none";

    }

    // When user clicks outside modal, close it
    window.onclick = function(event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    }

    $(document).ready(function() {

        // Render income, expense etc values from AJAX calls
        $('#expense-val').text("$"+thisMonthExpense);

        $("#income-val").text(thisMonthIncome);

        $('#total-balance').text("$"+totalBalance);

        // Can access AJAX data here

    });
</script>
</html>