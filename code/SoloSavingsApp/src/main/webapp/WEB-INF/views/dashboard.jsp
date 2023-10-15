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
            text-decoration: underline;
            cursor: pointer;
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
         .add-expense-btn,.budget-goals-btn, #analytics-button{
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
            align-items: center; /* Center-align content horizontally within each form group */
            margin-bottom: 20px; /* Add some vertical spacing between form groups */
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
            <li id="logout"><a onclick="logout()" style="color: white; text-decoration: underline;">Logout</a></li>
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


    <div class="buttons">
        <!-- Add Income Button -->
        <button class="add-income-btn">Add Income</button>

        <!-- Add Income Button -->
        <button class="add-expense-btn">Add Expense</button>

        <!-- View Transaction History -->
        <button class="view-transactions-btn">View Transactions</button>

        <!-- Budget Goals Button -->
        <button class="budget-goals-btn">View Budget Goals</button>

        <button id="transaction-history-button">Download Transaction</button>

        <!-- Show 12 mon Button -->
        <button id="analytics-button">View Analytics</button>


    </div>
    <%--income button and modal--%>

    <!-- Add Income Modal -->
    <div id="add-income-modal" class="modal">
        <!-- Modal content -->
        <div class="modal-content">
            <span class="close">&times;</span>
            <h2>Add Your Income</h2>
            <form id="add-income-form">
                <div class="form-group">
                    <label for="income-source">Source:</label>
                    <input type="text" class="income-source" id="income-source">
                </div>
                <div class="form-group">
                    <label for="income-amount">Amount:</label>
                    <input type="number" step="any" class="income-amount" id="income-amount">
                </div>
                <div class="form-group">
                    <button class="submit-income-btn" id="submit-income-btn" type="submit">Submit</button>
                </div>
            </form>
        </div>
    </div>

    <!-- Expense Modal -->
    <div id="add-expense-modal" class="modal">

        <!-- Modal content -->
        <div class="modal-content">
            <span class="close">&times;</span>
            <h2>Add Your Expense</h2>
            <form id="add-expense-form">
                <div class="form-group">
                    <label for="expense-source">Source:</label>
                    <input type="text" class="expense-source" id="expense-source">
                </div>
                <div class="form-group">
                    <label for="expense-amount">Amount:</label>
                    <input type="number" step="any" class="expense-amount" id="expense-amount">
                </div>
                <div class="form-group">
                    <button type="submit">Submit</button>
                </div>
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
    const EXPIRE_COOKIE = "jwtToken=; expires= Thur, 01 Jan 1970 00:00:00 UTC; path=/solosavings;";
    function setAuthHeader() {
        const jwtToken = document.cookie.replace(/(?:(?:^|.*;\s*)jwtToken\s*\=\s*([^;]*).*$)|^.*$/, "$1");
        if(jwtToken) {
            jwt = jwtToken;
            console.log("Found JWT cookie");

        } else {
            console.log("Failed to find JWT cookie");
            alert("You must login before accessing the dashboard");
            window.location.replace("/solosavings/login");
        }
    }

    function logout() {
        console.log("Logging out");
        document.cookie = EXPIRE_COOKIE;
        window.location.replace("/");
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
        url: '/api/transaction/thismonth/CREDIT',
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
        url: '/api/transaction/thismonth/DEBIT',
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
    var incomeModal = document.getElementById("add-income-modal");
    var expenseModal = document.getElementById("add-expense-modal");

    // When "Add Income/Exp" button clicked...
    $(".add-income-btn").click(function() {
        // Show the modal
        incomeModal.style.display = "block";
    });
    $(".add-expense-btn").click(function() {
        // Show the modal
        expenseModal.style.display = "block";
    });
    $(".view-transactions-btn").click(function() {
        console.log("view transaction history page...");
        window.location.replace("/solosavings/transactionHistory");
    });
    $(".budget-goals-btn").click(function() {
        console.log("view budget goals page...");
        window.location.replace("/solosavings/budgetGoals");
    });
    $("#analytics-button").click(function() {
        window.location.replace("analytics");
    });
    // When user clicks the close button for income modal
    var closeIncomeModal = incomeModal.getElementsByClassName("close")[0];
    closeIncomeModal.onclick = function() {
        // Hide the income modal
        incomeModal.style.display = "none";
    };

    // When user clicks the close button for expense modal
    var closeExpenseModal = expenseModal.getElementsByClassName("close")[0];
    closeExpenseModal.onclick = function() {
        // Hide the expense modal
        expenseModal.style.display = "none";
    };

    // When user clicks outside modal, close it
    window.onclick = function(event) {
        if (event.target === incomeModal || event.target === expenseModal) {
            incomeModal.style.display = "none";
            expenseModal.style.display = "none";
        }
    }

    $(document).ready(function() {

        // Render income, expense etc values from AJAX calls
        $('#expense-val').text("$"+thisMonthExpense);
        $("#income-val").text("$"+thisMonthIncome);
        $('#total-balance').text("$"+totalBalance);

        // Handle income form submission
        $("#add-income-form").submit(function(event) {
            event.preventDefault(); // Prevent the default form submission behavior
            const incomeAmount = parseFloat(document.getElementById("income-amount").value);
            const incomeSource = document.getElementById("income-source").value;
            const formData = {
                transaction_id: null,
                user_id: null,
                amount: incomeAmount,
                source: incomeSource,
                transaction_type: "CREDIT",
                transaction_date: null
            }

            $.ajax({
                type: "POST",
                url: "/api/transaction/add", // Replace with your actual server endpoint
                contentType: 'application/json',
                data: JSON.stringify(formData),
                success: function(response) {
                    console.log("Income added successfully");
                    $("#add-income-modal").hide();
                    location.reload();
                },
                error: function(error) {
                    console.error("Error adding income", error);
                }
            });
        });

        // Handle expense form submission
        $("#add-expense-form").submit(function(event) {
            event.preventDefault(); // Prevent the default form submission behavior
            const expAmount = parseFloat(document.getElementById("expense-amount").value);
            const expSource = document.getElementById("expense-source").value;
            const formData = {
                transaction_id: null,
                user_id: null,
                amount: expAmount,
                source: expSource,
                transaction_type: "DEBIT",
                transaction_date: null
            }

            $.ajax({
                type: "POST",
                url: "/api/transaction/add", // Replace with your actual server endpoint
                contentType: 'application/json',
                data: JSON.stringify(formData),
                success: function(response) {
                    // Handle success, if needed
                    console.log("Expense added successfully");
                    $("#add-expense-modal").hide();
                    location.reload();
                },
                error: function(error) {
                    console.error("Error adding expense", error);
                }
            });
        });

    });
    $("#transaction-history-button").click(function() {
        console.log("Button clicked"); // Add this line for debugging
        $.ajax({
            type: "GET",
            url: "/api/transaction/export/csv", // Replace with the actual URL, e.g., "/solosavings/123/export/csv"
            	success: function(response) {
            	    console.log("Transaction history exported successfully");
            	    // Create a hidden anchor element and set the href attribute to the response data
            	    const anchor = document.createElement("a");
            	    anchor.href = URL.createObjectURL(new Blob([response]));

            	    // Set the download attribute to specify the filename
            	    anchor.setAttribute("download", "transaction_history.csv");

            	    // Trigger a click event on the anchor element to initiate the download
            	    anchor.click();
            	},
            error: function(error) {
                console.error("Error exporting transaction history", error);
                // Handle the error, e.g., show an error message to the user.
            }
        });
    });
</script>
</html>