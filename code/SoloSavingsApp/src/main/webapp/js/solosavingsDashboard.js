let jwt = "";
const EXPIRE_COOKIE = "jwtToken=; expires= Thur, 01 Jan 1970 00:00:00 UTC; path=/solosavings;";
function setAuthHeader() {
    const jwtToken = document.cookie.replace(/(?:(?:^|.*;\s*)jwtToken\s*\=\s*([^;]*).*$)|^.*$/, "$1");
    if(jwtToken) {
        jwt = jwtToken;
        console.log("Found JWT cookie");

    } else {
        console.log("Failed to find JWT cookie");
        alert("You must login before accessing the dashboard");
        window.location.replace("/solosavings/login");
    }
}

setAuthHeader();

let thisMonthIncome;
let thisMonthExpense;
let totalBalance;
$.ajaxSetup({
    headers: {
        'Authorization': "Bearer " + jwt
    }
})
$.ajax({
    async: false,
    type: 'GET',
    url: '/api/transaction/thismonth/CREDIT',
    contentType: 'application/json',
    success: function(response) {
        thisMonthIncome = response;
    },
    error: function(error) {
        console.error('Something went wrong!', error);
    }
});
$.ajax({
    async: false,
    type: 'GET',
    url: '/api/transaction/thismonth/DEBIT',
    contentType: 'application/json',
    success: function(response) {
        thisMonthExpense = response;

    },
    error: function(error) {
        console.error('Something went wrong!', error);
    }
});
$.ajax({
    async: false,
    type: 'GET',
    url: '/api/user/balance',
    contentType: 'application/json',
    success: function(response) {
        totalBalance = response;
    },
    error: function(error) {
        console.error('Something went wrong!', error);
    }
});

// Get modal element
var incomeModal = document.getElementById("add-income-modal");
var expenseModal = document.getElementById("add-expense-modal");

// When "Add Income/Exp" button clicked...
$(".add-income-btn").click(function() {
    // Show the modal
    incomeModal.style.display = "block";
});
$(".add-expense-btn").click(function() {
    // Show the modal
    expenseModal.style.display = "block";
});
$(".view-transactions-btn").click(function() {
    console.log("view transaction history page...");
    window.location.replace("/solosavings/transactionHistory");
});
$(".budget-goals-btn").click(function() {
    console.log("view budget goals page...");
    window.location.replace("/solosavings/budgetGoals");
});
$("#analytics-button").click(function() {
    window.location.replace("analytics");
});
// When user clicks the close button for income modal
var closeIncomeModal = incomeModal.getElementsByClassName("close")[0];
closeIncomeModal.onclick = function() {
    // Hide the income modal
    incomeModal.style.display = "none";
};

// When user clicks the close button for expense modal
var closeExpenseModal = expenseModal.getElementsByClassName("close")[0];
closeExpenseModal.onclick = function() {
    // Hide the expense modal
    expenseModal.style.display = "none";
};

// When user clicks outside modal, close it
window.onclick = function(event) {
    if (event.target === incomeModal || event.target === expenseModal) {
        incomeModal.style.display = "none";
        expenseModal.style.display = "none";
    }
}

$(document).ready(function() {

    // Render income, expense etc values from AJAX calls
    $('#expense-val').text("$"+thisMonthExpense);
    $("#income-val").text("$"+thisMonthIncome);
    $('#total-balance').text("$"+totalBalance);

    // Handle income form submission
    $("#add-income-form").submit(function(event) {
        event.preventDefault(); // Prevent the default form submission behavior
        const incomeAmount = parseFloat(document.getElementById("income-amount").value);
        const incomeSource = document.getElementById("income-source").value;
        const formData = {
            transaction_id: null,
            user_id: null,
            amount: incomeAmount,
            source: incomeSource,
            transaction_type: "CREDIT",
            transaction_date: null
        }

        $.ajax({
            type: "POST",
            url: "/api/transaction/add", // Replace with your actual server endpoint
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function(response) {
                console.log("Income added successfully");
                $("#add-income-modal").hide();
                location.reload();
            },
            error: function(error) {
                console.error("Error adding income", error);
            }
        });
    });

    // Handle expense form submission
    $("#add-expense-form").submit(function(event) {
        event.preventDefault(); // Prevent the default form submission behavior
        const expAmount = parseFloat(document.getElementById("expense-amount").value);
        const expSource = document.getElementById("expense-source").value;
        const formData = {
            transaction_id: null,
            user_id: null,
            amount: expAmount,
            source: expSource,
            transaction_type: "DEBIT",
            transaction_date: null
        }

        $.ajax({
            type: "POST",
            url: "/api/transaction/add", // Replace with your actual server endpoint
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function(response) {
                // Handle success, if needed
                console.log("Expense added successfully");
                $("#add-expense-modal").hide();
                location.reload();
            },
            error: function(error) {
                console.error("Error adding expense", error);
            }
        });
    });

});
$("#transaction-history-button").click(function() {
    console.log("Button clicked"); // Add this line for debugging
    $.ajax({
        type: "GET",
        url: "/api/transaction/export/csv", // Replace with the actual URL, e.g., "/solosavings/123/export/csv"
        success: function(response) {
            console.log("Transaction history exported successfully");
            // Create a hidden anchor element and set the href attribute to the response data
            const anchor = document.createElement("a");
            anchor.href = URL.createObjectURL(new Blob([response]));

            // Set the download attribute to specify the filename
            anchor.setAttribute("download", "transaction_history.csv");

            // Trigger a click event on the anchor element to initiate the download
            anchor.click();
        },
        error: function(error) {
            console.error("Error exporting transaction history", error);
            // Handle the error, e.g., show an error message to the user.
        }
    });
});