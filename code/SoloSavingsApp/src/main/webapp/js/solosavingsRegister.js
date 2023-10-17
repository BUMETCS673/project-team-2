function isValidationCheckPassed() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password_hash").value;
    const confirmPassword = document.getElementById("confirm_password").value;
    const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;

    if (username.length < 6) {
        alert("Username must be at least 6 characters long.");
        return false;
    }

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
    $('#registrationForm').submit(function(event) {
        event.preventDefault();
        if(!isValidationCheckPassed()) return;

        const formData = {
            username: $('input[name="username"]').val().toLowerCase(),
            password_hash: $('input[name="password_hash"]').val(),
            email: $('input[name="email"]').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/register',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function(response) {
                console.log('Registration successful:', response);
                confirm("Your account successfully created, redirect to your dashboard.")
                window.location.replace("login");
            },
            error: function(error) {
                alert(error.responseText);
                console.error('Registration failed:', error);
            }
        });
    });
});