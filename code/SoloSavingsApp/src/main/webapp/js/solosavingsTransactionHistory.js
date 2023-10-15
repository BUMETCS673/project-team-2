
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
        { "mData": null, "defaultContent": "<button>Delete</button>"},
        { "mData": null, "defaultContent": "<button>Add Comment</button><button>View Comments</button>"}
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
    let button = e.target
    if(button.textContent == 'Delete') {
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
    } else if (button.textContent == 'Add Comment') {
        addCommentsList.style.display="block";
        commentModal.style.display = "block";
        commentsList.style.display="none";
    } else if(button.textContent == 'View Comments') {
        $.ajax({
            type: "GET",
            url: "/comments/list/"+transaction_id,
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
        url: "/comments/add/"+transaction_id, // Replace with your actual server endpoint
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
        url: "/comments/list/"+transaction_id,
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
        url: "/comments/add/"+transaction_id, // Replace with your actual server endpoint
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
