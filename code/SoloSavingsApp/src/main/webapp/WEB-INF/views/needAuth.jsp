<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>404 - Not Found</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            text-align: center;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
        }

        h1 {
            color: #333;
        }

        p {
            color: #666;
        }

        .back-link {
            margin-top: 20px;
            text-align: center;
        }

        .back-link a {
            text-decoration: none;
            color: #007bff;
            cursor: pointer;
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
    </style>
</head>

<body>
<div class="container">
    <h1>You are not authenticated</h1>
    <p>You need to log in to your account in order to view the page.</p>

    <div class="back-link">
        <a href="/">Go back to the homepage</a>
    </div>
</div>
</body>

<footer>
    &copy; 2023 SoloSavings. All rights reserved.
</footer>

</html>