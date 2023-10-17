<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>SoloSavings Dashboard</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
<jsp:include page="templates/header.jsp" />
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
<jsp:include page="templates/footer.jsp" />
</body>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="/js/solosavingsDashboard.js"></script>
</html>