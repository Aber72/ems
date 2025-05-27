<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Employee</title>
</head>
<body>
<div class="container">
    <h2>✏️ Edit Employee</h2>

    <c:if test="${not empty errorMessage}">
        <div class="msg error">${errorMessage}</div>
    </c:if>
    <c:if test="${not empty successMessage}">
        <div class="msg success">${successMessage}</div>
    </c:if>

    <form:form method="post" action="update" modelAttribute="employee">
        <form:hidden path="uid"/>

        <label>👤 First Name:</label>
        <form:input path="firstName"/>

        <label>🧑 Middle Name:</label>
        <form:input path="middleName"/>

        <label>👤 Last Name:</label>
        <form:input path="lastName"/>

        <label>🗓️ Birth Date:</label>
        <form:input path="birthDate" type="date"/>

        <label>🧑‍💼 Position:</label>
        <form:input path="position"/>

        <input type="submit" value="Update" class="btn"/>
    </form:form>

    <br><br>
    <a href="<c:url value='/employee/list'/>">⬅️ Back to List</a>
</div>

</body>
</html>