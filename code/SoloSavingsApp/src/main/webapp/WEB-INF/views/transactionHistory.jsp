<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <title>SoloSavings Transaction History</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
    <jsp:include page="templates/header.jsp" />
    <div>
        <h1>View your transaction history</h1>
    </div>
    <main>
    <div class="container">
        <table id="transactionHistoryDataTable">
            <thead>
            <tr>
                <th>Transaction Id</th>
                <th>Transaction Date</th>
                <th>Source</th>
                <th>Transaction Type</th>
                <th>Amount</th>
                <th>Actions</th>
            </tr>
            </thead>
        </table>
    </div>
    </main>
    <jsp:include page="templates/footer.jsp" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
    <script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootbox.js/6.0.0/bootbox.js"></script>
    <script src="/js/solosavingsTransactionHistory.js"></script>
</body>
c</html>