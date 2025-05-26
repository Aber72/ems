<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Employee</title>
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
    
    
    