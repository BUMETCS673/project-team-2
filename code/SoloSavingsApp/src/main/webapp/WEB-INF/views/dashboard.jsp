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

        h1 {
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
        <section id="transactions">
            <h2>Transactions</h2>
            <table>
                <thead>
                <tr>
                    <th>Date</th>
                    <th>Description</th>
                    <th>Amount</th>
                </tr>
                </thead>
                <tbody>
                <!-- Loop through transactions and populate the table -->
                <c:forEach items="${transactions}" var="transaction">
                    <tr>
                        <td><c:out value="${transaction.date}" /></td>
                        <td><c:out value="${transaction.description}" /></td>
                        <td><c:out value="${transaction.amount}" /></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </section>
        <section id="budget-summary">
            <h2>Budget Summary</h2>
            <p>Total Income: $<c:out value="${totalIncome}" /></p>
            <p>Total Expenses: $<c:out value="${totalExpenses}" /></p>
            <p>Remaining Budget: $<c:out value="${remainingBudget}" /></p>
        </section>
    </main>
    <footer>
        &copy; 2023 SoloSavings
    </footer>
</body>
</html>