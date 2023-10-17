function isValidationCheckPassed() {
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirm_password").value;
    const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;

    if (!passwordPattern.test(password)) {
        alert("Password must contain at least one uppercase letter, one lowercase letter, one digit, and be at least 8 characters long.");
        return false;
    }

    if (password !== confirmPassword) {
        alert("Passwords do not match.");
        return false;
    }

    return true;
}

$(document).ready(function() {
    $('#resetForm').submit(function(event) {
        event.preventDefault();
        if(!isValidationCheckPassed()) return;
        const formData = {
            token: $('input[name="token"]').val(),
            username: $('input[name="username"]').val(),
            password: $('input[name="password"]').val(),
        };

        $.ajax({
            type: 'POST',
            url: '/api/reset-password',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function(response) {
                console.log('Reset password successfully:', response);
                alert("Password reset successfully, redirect to login page.");
                window.location.replace("/solosavings/login");
            },
            error: function(error) {
                alert('Something went wrong, please try again.');
                console.error('Something went wrong:', error);
            }
        });
    });
});