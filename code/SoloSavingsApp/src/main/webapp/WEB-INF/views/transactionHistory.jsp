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
            position: relative;
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
        .modal-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 20%;
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

    <!-- Add Comment Modal -->
    <div id="add-comment-modal" class="modal">
        <!-- Modal content -->
        <div class="modal-content">
            <span class="close">&times;</span>
            <h2>Your Comments</h2>
            <div class="comments-section" id="add-comments-list">
                <h2 style="clear: both">Comments</h2>
                <textarea id="comment-input" placeholder="Write your comment here" style="width: 85%;float: left"></textarea>
                <button id="submit-comment" style="width: 10%;float: left;margin-top:5px;margin-left: 15px;">Submit</button>

            </div>
            <div id="comments-list" style="clear: both">

            </div>
        </div>
    </div>


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
                <th>Comments Actions</th>
            </tr>
            </thead>


        </table>
    </div>

</main>
</body>
<footer>
    &copy; 2023 SoloSavings
</footer>
<style>
    .comments-section {
        margin-top: 20px;
        padding: 20px;
        background-color: #fff;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }

    #comment-input {
        width: 100%;
        padding: 10px;
        margin-bottom: 10px;
        resize: none;
    }

    #submit-comment {
        background-color: #007acc;
        color: white;
        border: none;
        padding: 10px 20px;
        cursor: pointer;
        border-radius: 5px;
    }

    #submit-comment:hover {
        background-color: #005fbb;
    }

    #comments-list {
        margin-top: 10px;
    }

    .comment {
        border: 1px solid #ccc;
        padding: 10px;
        margin-bottom: 10px;
    }
</style>
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
    var transactionId;
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
            {"mData": null, "defaultContent": "<button>Delete</button>"},
            {"mData": null, "defaultContent": "<button>Add Comment</button><button>View Comments</button>"}
        ]
    })

    var commentModal = document.getElementById("add-comment-modal");
    var commentsList = document.getElementById("comments-list");
    var addCommentsList = document.getElementById("add-comments-list");
    var closeCommentModal = commentModal.getElementsByClassName("close")[0];
    closeCommentModal.onclick = function() {
        commentModal.style.display = "none";
    };
    table.on('click', 'button', function (e) {
        let transaction_id = e.target.closest('tr').firstChild.innerText;
        transactionId=transaction_id;
        let button = e.target;
        if (button.textContent === 'Delete'){
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
        }else if(button.textContent === 'Add Comment'){
            addCommentsList.style.display="block";
            commentModal.style.display = "block";
            commentsList.style.display="none";
        }else if(button.textContent === 'View Comments'){
            $.ajax({
                type: "GET",
                url: "/comments/list/"+transactionId,
                contentType: 'application/json',
                success: function(response) {
                    displayComments(response);
                },
                error: function(error) {
                    console.error("Error getting comments", error);
                }
            });
            commentsList.style.display="block";
            commentModal.style.display = "block";
            addCommentsList.style.display="none";
        }

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

    $("#submit-comment").click(function() {
        const commentText = $("#comment-input").val();
        const commentData = {
            content: commentText
        };
        $.ajax({
            type: "POST",
            url: "/comments/add/"+transactionId, // Replace with your actual server endpoint
            contentType: 'application/json',
            data: JSON.stringify(commentData),
            success: function(response) {
                console.log("Income added successfully");
                location.reload();
            },
            error: function(error) {
                console.error("Error adding income", error);
                location.reload();
            }
        });
    });


    $(document).ready(function() {
        $.ajax({
            type: "GET",
            url: "/comments/list/"+transactionId,
            contentType: 'application/json',
            success: function(response) {
                displayComments(response);
            },
            error: function(error) {
                console.error("Error getting comments", error);
            }
        });
    });
    function displayComments(comments) {
        const commentsList = $("#comments-list");

        commentsList.empty();

        $.each(comments, function(index, comment) {
            const commentHtml =
                '<div class="comment">'+
                '<p>'+ comment.content +'</p>'+
                '<button class="edit-comment" id="+'+comment.id+'" style="margin-left: 40%";>Edit</button>'+
                '<button class="delete-comment" id="+'+comment.id+'" style="margin-left: 20px;">Delete</button>'+
                '</div>';
            $("#comments-list").append(commentHtml);
        });
    }


    $("#comments-list").on("click", ".edit-comment", function() {
        const commentId = $(this).attr("id");
        const commentDiv = $(this).closest(".comment");
        const commentText = commentDiv.find("p").text();
        const editedCommentText = prompt("Edit your comment:", commentText);
        const updateData = {
            id:$(this).attr("id"),
            content: editedCommentText
        };
        $.ajax({
            type: "POST",
            url: "/comments/add/"+transactionId, // Replace with your actual server endpoint
            contentType: 'application/json',
            data: JSON.stringify(updateData),
            success: function(response) {
                console.log("Income added successfully");
                location.reload();
            },
            error: function(error) {
                console.error("Error adding income", error);
            }
        });

    });

    $("#comments-list").on("click", ".delete-comment", function() {
        const commentId = $(this).attr("id");
        $.ajax({
            type: "POST",
            url: "/comments/del/"+commentId, // Replace with your actual server endpoint
            contentType: 'application/json',
            success: function(response) {
                location.reload();
            },
            error: function(error) {
            }
        });
    });

</script>
</html>