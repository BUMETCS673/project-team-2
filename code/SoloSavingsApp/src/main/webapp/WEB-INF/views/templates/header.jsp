<%--
  Created by IntelliJ IDEA.
  User: cprat
  Date: 10/13/2023
  Time: 11:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header>
    <nav>
        <ul id="logged_out_user">
            <li id="homeOut"><a href="/solosavings" style="color: white;">Home</a></li>
            <li id="login"><a href="/solosavings/login" style="color: white;">Login</a></li>
            <li id="register"><a href="/solosavings/register" style="color: white;">Register</a></li>
        </ul>
        <ul id="logged_in_user">
            <li id="homeIn"><a href="/solosavings" style="color: white;">Home</a></li>
            <li id="dashboard"><a href="/solosavings/dashboard" style="color: white;">Dashboard</a></li>
            <li id="transactionhistory"><a href="/solosavings/transactionHistory" style="color: white;">Transaction History</a></li>
            <li id="budgetgoals"><a href="/solosavings/budgetGoals" style="color: white;">Budget Goals</a></li>
            <li id="analytics"><a href="/solosavings/analytics" style="color: white;">Analytics</a></li>
            <li id="logout"><a href="#" onclick="logout()" style="color: white;">Logout</a></li>
        </ul>
    </nav>
</header>
<script src="/js/solosavingsMenu.js"></script>
