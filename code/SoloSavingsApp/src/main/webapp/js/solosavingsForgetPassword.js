$(document).ready(function() {
    $('#forgetForm').submit(function(event) {
        event.preventDefault();
        const formData = {
            username: $('input[name="username"]').val().toLowerCase(),
        };

        $.ajax({
            type: 'POST',
            url: '/api/forget-password',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function(response) {
                console.log('User found, email sent:', response);
                alert("User found, email sent. \nPlease check your inbox (and spam folder) for instructions. \nPlease DO NOT close the browser.");
                window.location.replace("/solosavings/reset-password");
            },
            error: function(error) {
                alert('Something went wrong, please try again.');
                $('#forgetForm')[0].reset();
                console.error('Something went wrong:', error);
            }
        });
    });
});