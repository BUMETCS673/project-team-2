<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <title>SoloSavings Budget Goals</title>
    <link href="/css/style.css" rel="stylesheet" type="text/css">
</head>

<body>
    <jsp:include page="templates/header.jsp" />
    <div>
        <h1>View your budget goals</h1>
    </div>
    <main>
         <div class="container">
            <table id="BudgetGoalsDataTable">
                <thead>
                <tr>
                    <th>Budget Goal ID</th>
                    <th>Save/Spend</th>
                    <th>Source</th>
                    <th>Target Amount</th>
                    <th>Current Amount</th>
                    <th>Actions</th>
                </tr>
                </thead>
            </table>
        </div>
        <div class="buttons">
            <!-- Add Budget Goal -->
            <button class="add-spend-btn">Add Budget Goal (Spend)</button>
            <button class="add-save-btn">Add Budget Goal (Save)</button>
        </div>

        <!-- Add Budget Goal Spend Modal -->
        <div id="add-spend-modal" class="modal">
            <!-- Modal content -->
            <div class="modal-contentBudgetGoal">
                <span class="close">&times;</span>
                <h2>Add Your Budget Goal</h2>
                <form id="add-spend-form">
                    <div class="form-group">
                        <label for="spend-source">Source:</label>
                        <input type="text" class="spend-source" id="spend-source">
                    </div>
                    <div class="form-group">
                        <label for="spend-amount">Amount:</label>
                        <input type="number" step="any" class="spend-amount" id="spend-amount">
                    </div>
                    <div class="form-group">
                        <button class="submit-spend-btn" id="submit-spend-btn" type="submit">Submit</button>
                    </div>
                </form>
            </div>
        </div>

        <!-- Add Budget Goal Save Modal -->
        <div id="add-save-modal" class="modal">
            <!-- Modal content -->
            <div class="modal-contentBudgetGoal">
                <span class="close">&times;</span>
                <h2>Add Your Budget Goal</h2>
                <form id="add-save-form">
                    <div class="form-group">
                        <label for="save-source">Source:</label>
                        <input type="text" class="save-source" id="save-source">
                    </div>
                    <div class="form-group">
                        <label for="save-amount">Amount:</label>
                        <input type="number" step="any" class="save-amount" id="save-amount">
                    </div>
                    <div class="form-group">
                        <button class="submit-save-btn" id="submit-save-btn" type="submit">Submit</button>
                    </div>
                </form>
            </div>
        </div>

    </main>
    <jsp:include page="templates/footer.jsp" />
</body>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootbox.js/6.0.0/bootbox.js"></script>
<script src="/js/solosavingsBudgetGoals.js"></script>
</html>