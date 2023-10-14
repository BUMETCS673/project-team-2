
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

    // Confirm box
    bootbox.confirm({
        title: 'Delete Transaction Record?',
        message: 'Do you really want to delete this transaction record now? This cannot be undone.',
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
                deleteTransaction(transaction_id);
            }
        }
    });

});

function deleteTransaction(transaction_id){
    return $.ajax({
        type: 'DELETE',
        url: '/api/transaction/delete/' + transaction_id,
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
