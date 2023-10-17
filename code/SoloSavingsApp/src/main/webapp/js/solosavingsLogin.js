function setJwtInCookie(jwtResponse) {
    console.log("Token: " + jwtResponse['token'] + " Expiry: " + jwtResponse['expiry']);
    document.cookie = "jwtToken=" + jwtResponse['token'] + "; expires=" + jwtResponse['expiry'];
}
$(document).ready(function() {
    $('#loginForm').submit(function(event) {
        event.preventDefault();
        const formData = {
            username: $('input[name="username"]').val().toLowerCase(),
            password: $('input[name="password"]').val(),
        };

        $.ajax({
            type: 'POST',
            url: '/api/login',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function(response) {
                console.log(response)
                setJwtInCookie(response);
                alert("Authentication passed successfully, redirect to your dashboard.");
                window.location.replace("/solosavings/dashboard");
            },
            error: function(error) {
                alert("Authentication failed, please try again");
                console.error('Login failed:', error);
            }
        });
    });
});