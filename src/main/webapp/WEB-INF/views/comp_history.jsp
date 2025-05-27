<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Compensation History</title>
    <meta charset="UTF-8">
    
     <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: #eef3f9;
            margin: 0;
            padding: 40px;
            display: flex;
            justify-content: center;
        }

        .container {
            background: #ffffff;
            padding: 40px;
            border-radius: 20px;
            box-shadow: 0 12px 30px rgba(0, 0, 0, 0.12);
            width: 100%;
            max-width: 850px;
            animation: fadeIn 0.8s ease-in-out;
        }

        .header-block {
            background: linear-gradient(to right, #003973, #e5e5be);
            color: white;
            padding: 24px;
            border-radius: 12px;
            box-shadow: 0 6px 14px rgba(0, 0, 0, 0.1);
            margin-bottom: 25px;
        }

        .header-block h2 {
            margin: 0 0 10px;
            font-size: 28px;
            font-weight: bold;
            letter-spacing: 1px;
        }

        .header-block p {
            margin: 0;
            font-size: 16px;
            font-weight: 500;
        }

        .error {
            background-color: #ffeded;
            color: #d00000;
            padding: 10px 16px;
            border-radius: 8px;
            margin-bottom: 16px;
            border-left: 4px solid #d00000;
            font-weight: 600;
        }