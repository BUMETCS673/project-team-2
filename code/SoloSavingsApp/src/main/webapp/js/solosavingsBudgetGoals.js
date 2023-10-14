
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
