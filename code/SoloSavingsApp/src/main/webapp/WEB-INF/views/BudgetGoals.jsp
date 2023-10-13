<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <title>SoloSavings Budget Goals</title>

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

        tfoot {
            font-weight: bold;
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
        .modal-contentBudgetGoal {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 20%;
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

        nav ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
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

        nav ul li {
            display: inline;
            margin-right: 20px;
            color: white;
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
            background-color: #ffffff;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .add-spend-btn, .add-save-btn{
            margin: 10px;
        }

        #add-spend-form, #add-save-form {
            text-align: center;
        }

        main {
            max-width: 60%;
            margin: 0 auto;
            padding: 2rem;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .form-group, .add-button {
            display: flex;
            flex-direction: column;
            align-items: center; /* Center-align content horizontally within each form group */
            margin-bottom: 20px; /* Add some vertical spacing between form groups */
        }

        h1, h2, h3, p {
            text-align: center;
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
</body>
<footer>
    &copy; 2023 SoloSavings
</footer>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootbox.js/6.0.0/bootbox.js"></script>
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
    $.ajaxSetup({
        headers: {
            'Authorization': "Bearer " + jwt
        }
    })

    var table = $('#BudgetGoalsDataTable').DataTable({
        "sAjaxSource": "/api/budgetgoal/all",
        "sAjaxDataProp": "",
        "order": [[ 0, "asc" ]],
        "aoColumns": [
            { "mData": "id"},
            { "mData": "budgetGoalType"},
            { "mData": "source" },
            { "mData": "targetAmount" },
            { "mData": "actualAmount"},
            {"mData": null, "defaultContent": "<button>Delete</button>"}
        ]
    })


    table.on('click', 'button', function (e) {
        let budgetgoal_id = e.target.closest('tr').firstChild.innerText;

        // Confirm box
        bootbox.confirm({
            title: 'Delete Budget Goal Record?',
            message: 'Do you really want to delete this budget goal record now? This cannot be undone.',
            buttons: {
                cancel: {
                    label: '<i class="fa fa-times"></i> Cancel'
                },
                confirm: {
                    label: '<i class="fa fa-check"></i> Confirm'
                }
            },
            callback: function (result) {
                if (result) {
                    //console.log("Delete selected");
                    deleteBudgetGoal(budgetgoal_id);
                }
            }
        });

    });

    var spendModal = document.getElementById("add-spend-modal");

    $(".add-spend-btn").click(function() {
        // Show the modal
        spendModal.style.display = "block";
    });

    // When user clicks the close button for budget goal spend modal
    var closespendModal = spendModal.getElementsByClassName("close")[0];
    closespendModal.onclick = function() {
        // Hide the spend modal
        spendModal.style.display = "none";
    };

    var saveModal = document.getElementById("add-save-modal");

    $(".add-save-btn").click(function() {
        // Show the modal
        saveModal.style.display = "block";
    });

    // When user clicks the close button for budget goal save modal
    var closesaveModal = saveModal.getElementsByClassName("close")[0];
    closesaveModal.onclick = function() {
        // Hide the save modal
        saveModal.style.display = "none";
    };

    $(document).ready(function() {

        // Handle spend form submission
        $("#add-spend-form").submit(function(event) {
            event.preventDefault(); // Prevent the default form submission behavior
            const spendAmount = parseFloat(document.getElementById("spend-amount").value);
            const spendSource = document.getElementById("spend-source").value;
            const formData = {
                id: null,
                userId: null,
                budgetGoalType: "SPEND",
                source: spendSource,
                targetAmount: spendAmount,
                startDate: null
            }

            $.ajax({
                type: "POST",
                url: "/api/budgetgoal/add", // Replace with your actual server endpoint
                contentType: 'application/json',
                data: JSON.stringify(formData),
                success: function(response) {
                    console.log("Budget goal added successfully");
                    $("#add-spend-modal").hide();
                    location.reload();
                },
                error: function(error) {
                    console.error("Error budget goal", error);
                }
            });
        });

        // Handle save form submission
        $("#add-save-form").submit(function(event) {
            event.preventDefault(); // Prevent the default form submission behavior
            const saveAmount = parseFloat(document.getElementById("save-amount").value);
            const saveSource = document.getElementById("save-source").value;
            const formData = {
                id: null,
                userId: null,
                budgetGoalType: "SAVE",
                source: saveSource,
                targetAmount: saveAmount,
                startDate: null
            }

            $.ajax({
                type: "POST",
                url: "/api/budgetgoal/add", // Replace with your actual server endpoint
                contentType: 'application/json',
                data: JSON.stringify(formData),
                success: function(response) {
                    console.log("Budget goal added successfully");
                    $("#add-save-modal").hide();
                    location.reload();
                },
                error: function(error) {
                    console.error("Error budget goal", error);
                }
            });
        });

    });

   function deleteBudgetGoal(budgetgoal_id){
        return $.ajax({
            type: 'DELETE',
            url: '/api/budgetgoal/delete/' + budgetgoal_id,
            contentType: 'application/json',
            success: function(response) {
                //console.log(response);
                window.location.reload();
            },
            error: function(error) {
                console.error('Something went wrong!', error);
                bootbox.alert(error);
            }
        });
    }

</script>
</html>