<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
    <title>SoloSavings Transaction History</title>

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
</body>
<footer>
    &copy; 2023 SoloSavings
</footer>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
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


    // let table = new DataTable('#transactionHistoryDataTable', {
    //     ajax: {
    //         url: '/api/transaction/history',
    //         type: 'GET',
    //         dataSrc: ""
    //     },
    //     columns: [
    //         {data: 'transaction_id'},
    //         {data: 'transaction_date'},
    //         {data: 'source'},
    //         {data: 'transaction_type'},
    //         {data: 'amount'},
    //         {"data": null, "defaultContent": "<button>Delete</button>"}
    //
    //     ],
    //     processing: true,
    //     serverSide: true,
    //     ordering: true,
    //     searching: true
    // });

    var table = $('#transactionHistoryDataTable').DataTable({
        "sAjaxSource": "/api/transaction/history",
        "sAjaxDataProp": "",
        "order": [[ 0, "asc" ]],
        "aoColumns": [
            { "mData": "transaction_id"},
            { "mData": "transaction_date" },
            { "mData": "source" },
            { "mData": "transaction_type" },
            { "mData": "amount" },
            {"mData": null, "defaultContent": "<button>Delete</button>"}
        ]
    })


    table.on('click', 'button', function (e) {
        let transaction_id = e.target.closest('tr').firstChild.innerText;

        alert(transaction_id);
        deleteTransaction(transaction_id);

    });

    $(document).ready(function() {
        let transactionHistory;

        async function fetchData() {
            transactionHistory = await getTransactionHistory();
        }

        function getTransactionHistory() {
            return $.ajax({
                type: 'GET',
                url: '/api/transaction/history',
                contentType: 'application/json',
                success: function(response) {
                    transactionHistory = response;
                    console.log(transactionHistory);

                },
                error: function(error) {
                    console.error('Something went wrong!', error);
                }
            });
        }
        async function init() {
            await fetchData();
        }
        init();
    });

    function deleteTransaction(transaction_id){
        console.log("deleting transaction " + transaction_id);
        return $.ajax({
            type: 'DELETE',
            url: '/api/transaction/delete/' + transaction_id,
            contentType: 'application/json',
            success: function(response) {

                console.log(response);
                window.location.reload();

            },
            error: function(error) {
                console.error('Something went wrong!', error);
            }
        });

    }
</script>
</html>