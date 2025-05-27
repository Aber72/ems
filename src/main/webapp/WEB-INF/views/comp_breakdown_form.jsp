<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Compensation Breakdown Form</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(to right, #f3e5f5, #e3f2fd);
            padding: 40px;
            margin: 0;
        }

        .container {
            max-width: 500px;
            margin: auto;
            background: #fff;
            padding: 30px 40px;
            border-radius: 12px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.1);
            animation: fadeIn 0.5s ease-in-out;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        h2 {
            text-align: center;
            color: #6a1b9a;
            margin-bottom: 30px;
        }

        label {
            font-weight: 600;
            margin-bottom: 8px;
            display: block;
            color: #333;
        }

        input[type="month"] {
            width: 100%;
            padding: 12px;
            margin-bottom: 20px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 6px;
            box-sizing: border-box;
        }

        input[type="submit"] {
            background-color: #7e57c2;
            color: white;
            padding: 12px 24px;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: bold;
            cursor: pointer;
            width: 100%;
            transition: background 0.3s ease, transform 0.2s ease;
        }

        input[type="submit"]:hover {
            background-color: #5e35b1;
            transform: scale(1.02);
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Compensation Breakdown</h2>
    <form action="breakdown" method="post">
        <input type="hidden" name="uid" value="${uid}" />
        
        <label for="yearMonth">Select Month (yyyy-MM):</label>
        <input type="month" name="yearMonth" required />
        
        <input type="submit" value="View Breakdown" />
    </form>
</div>
</body>
</html>
