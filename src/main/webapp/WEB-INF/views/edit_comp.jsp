<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Compensation</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(to right, #e3f2fd, #fce4ec);
            margin: 0;
            padding: 40px;
        }

        .container {
            max-width: 600px;
            margin: auto;
            background: #ffffff;
            padding: 30px 40px;
            border-radius: 12px;
            box-shadow: 0 10px 25px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            color: #4a148c;
            margin-bottom: 30px;
        }

        label {
            font-weight: 600;
            margin-bottom: 6px;
            display: block;
            color: #333;
        }

        input[type="number"],
        textarea {
            width: 100%;
            padding: 12px;
            border-radius: 6px;
            border: 1px solid #ccc;
            margin-bottom: 20px;
            font-size: 15px;
            box-sizing: border-box;
        }

        input[type="number"]:focus,
        textarea:focus {
            border-color: #7e57c2;
            outline: none;
        }

        button {
            background-color: #7e57c2;
            color: white;
            padding: 12px 24px;
            border: none;
            font-size: 16px;
            border-radius: 8px;
            cursor: pointer;
            font-weight: bold;
            display: block;
            width: 100%;
            transition: background 0.3s ease, transform 0.2s ease;
        }

        button:hover {
            background-color: #5e35b1;
            transform: scale(1.02);
        }

        .back-link {
            display: block;
            margin-top: 20px;
            text-align: center;
            color: #5e35b1;
            text-decoration: none;
            font-weight: 600;
        }

        .back-link:hover {
            text-decoration: underline;
        }

        .message {
            text-align: center;
            font-weight: bold;
            margin-bottom: 20px;
        }

        .message.error {
            color: red;
        }

        .message.success {
            color: green;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Edit Compensation Entry</h2>

    <c:if test="${not empty message}">
        <div class="message error">${message}</div>
    </c:if>
    <c:if test="${not empty msg}">
        <div class="message success">${msg}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/compensation/update" method="post">
    <input type="hidden" name="id" value="${compensation.id}" />
    <input type="hidden" name="uid" value="${uid}" />
    <input type="hidden" name="yearMonth" value="${yearMonth}" />

    <p><strong>Type:</strong> ${compensation.type}</p>
    <p><strong>Date:</strong> ${compensation.date}</p>

    <label for="amount">Amount:</label>
    <input type="number" name="amount" value="${compensation.amount}" step="0.01" required />

    <label for="description">Description:</label>
    <textarea name="description" rows="3" required>${compensation.description}</textarea>

    <button type="submit">Update</button>
	</form>


    <a class="back-link" href="${pageContext.request.contextPath}/compensation/breakdown?uid=${uid}&yearMonth=${yearMonth}">
    ← Back to Compensation Breakdown
   </a>
</div>

</body>
</html>
