<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Employee</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f3f4f6;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 600px;
            margin: 50px auto;
            padding: 30px;
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
        }
        h2 {
            text-align: center;
            color: #1a237e;
        }
        label {
            font-weight: bold;
            display: block;
            margin-top: 20px;
            color: #333;
        }
        input {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .btn {
            margin-top: 25px;
            width: 100%;
            padding: 12px;
            background-color: #5fb3ad;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }
        .btn:hover {
            background-color: #81c7c1;
        }
        .error {
            color: red;
            font-size: 14px;
        }
        .toast {
            visibility: hidden;
            min-width: 250px;
            background-color: #4caf50;
            color: white;
            text-align: center;
            border-radius: 6px;
            padding: 16px;
            position: fixed;
            z-index: 1;
            left: 50%;
            top: 20px;
            transform: translateX(-50%);
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
            font-size: 16px;
        }
        .toast.show {
            visibility: visible;
            animation: fadein 0.5s, fadeout 0.5s 3s;
        }
        @keyframes fadein {
            from {top: 0; opacity: 0;}
            to {top: 20px; opacity: 1;}
        }
        @keyframes fadeout {
            from {top: 20px; opacity: 1;}
            to {top: 0; opacity: 0;}
        }
        .back-link {
            display: block;
            text-align: center;
            margin-top: 20px;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="toast" id="toast"></div>

<div class="container">
    <h2>Add Employee</h2>
    
    
    <form:form method="post" action="add" modelAttribute="employee">
        <form:errors path="*" cssClass="error"/>

        <label>First Name:</label>
        <form:input path="firstName" required="true" />
        <form:errors path="firstName" cssClass="error" />

        <label>Middle Name:</label>
        <form:input path="middleName" />
        <form:errors path="middleName" cssClass="error" />

        <label> Last Name:</label>
        <form:input path="lastName" required="true" />
        <form:errors path="lastName" cssClass="error" />

        <label>Birth Date:</label>
        <form:input path="birthDate" type="date" required="true" />
        <form:errors path="birthDate" cssClass="error" />

        <label>Position:</label>
        <form:input path="position" required="true" />
        <form:errors path="position" cssClass="error" />

        <button type="submit" class="btn"> Add Employee</button>
    </form:form>
        <a class="back-link" href="<c:url value='/'/>">Back to Home</a>
</div>

<script type="module">
    const toast = document.getElementById("toast");

    const showToast = (message, color = '#4caf50') => {
        toast.textContent = message;
        toast.style.backgroundColor = color;
        toast.classList.add("show");
        setTimeout(() => toast.classList.remove("show"), 4000);
    };

    <%-- Toast using JSTL flags --%>
    <c:if test="${not empty msg}">
        showToast('${msg}');
    </c:if>
    <c:if test="${not empty error}">
        showToast('${error}', '#e53935');
    </c:if>
</script>
</body>
</html>
    
    
    