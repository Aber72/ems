<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Compensation Breakdown</title>

    <!-- Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

    <style>
        body {
            margin: 0;
            padding: 40px;
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(to right, #e3f2fd, #f3e5f5);
        }

        .container {
            max-width: 900px;
            margin: auto;
            background: white;
            padding: 40px;
            border-radius: 20px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
            animation: fadeIn 0.7s ease;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(40px); }
            to { opacity: 1; transform: translateY(0); }
        }

        h2 {
            text-align: center;
            color: #5e35b1;
            margin-bottom: 30px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
        }

        th, td {
            padding: 14px;
            text-align: left;
        }

        th {
            background-color: #7e57c2;
            color: white;
            font-weight: 600;
            text-transform: uppercase;
            font-size: 14px;
        }

        tr:nth-child(even) {
            background-color: #f3e5f5;
        }

        tr:hover {
            background-color: #ede7f6;
            transition: 0.3s ease;
        }

        td {
            font-size: 15px;
            color: #333;
        }

        .bold {
            font-weight: bold;
            color: #4a148c;
        }

        .total-row {
            margin-top: 30px;
            text-align: right;
            font-size: 20px;
            font-weight: bold;
            color: #2e1662;
            background: #ede7f6;
            padding: 15px;
            border-radius: 12px;
            box-shadow: 0 0 8px rgba(0,0,0,0.1);
        }

        .total-row i {
            margin-right: 8px;
            color: #7e57c2;
        }

        .back-link {
            display: inline-block;
            margin-top: 30px;
            font-size: 16px;
            font-weight: bold;
            color: #ffffff;
            background-color: #7e57c2;
            padding: 10px 18px;
            border-radius: 8px;
            text-decoration: none;
            transition: background 0.3s ease, transform 0.2s ease;
        }

        .back-link:hover {
            background-color: #5e35b1;
            transform: scale(1.05);
        }

        .back-icon {
            margin-right: 8px;
        }
    </style>
    
    </head>
<body>
<div class="container">
    <h2><i class="fas fa-chart-pie"></i> Compensation Breakdown for ${yearMonth}</h2>

    <table>
        <tr>
            <th>Date</th>
            <th>Type</th>
            <th>Amount</th>
            <th>Description</th>
        </tr>
        <c:forEach var="comp" items="${compensations}">
            <tr>
                <td>${comp.date}</td>
                <td>${comp.type}</td>
                <td>₹${comp.amount}</td>
                <td>
                    <c:choose>
                        <c:when test="${fn:length(comp.description) > 50}">
                            ${fn:substring(comp.description, 0, 50)}...
                        </c:when>
                        <c:otherwise>
                            ${comp.description}
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </table>