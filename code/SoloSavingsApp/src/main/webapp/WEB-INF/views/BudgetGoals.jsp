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

        nav ul li {
            display: inline;
            margin-right: 20px;
            color: white;
        }

        main {
            max-width: 60%;
            margin: 0 auto;
            padding: 2rem;
            background-color: #fff;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
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
            { "mData": null },
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

   function deleteBudgetGoal(budgetgoal_id){
        return $.ajax({
            type: 'DELETE',
            url: '/api/budgetGoal/delete/' + budgetgoal_id,
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