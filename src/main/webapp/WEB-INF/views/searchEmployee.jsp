<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Search Employees</title>
</head>
<body>
<h2>🔍 Search Employees</h2>

<form action="search" method="get">
    <label>First Name:</label>
    <input type="text" name="firstName" value="${firstName}" /><br>

    <label>Last Name:</label>
    <input type="text" name="lastName" value="${lastName}" /><br>

    <label>Position:</label>
    <input type="text" name="position" value="${position}" /><br>

    <input type="submit" value="Search" />
    <input type="reset" value="Clear" />
</form>
<h3>📄 Search Results:</h3>

<c:choose>
    <c:when test="${not empty employees}">
        <table>
            <tr>
                <th>UID</th>
                <th>First Name</th>
                <th>Middle Name</th>
                <th>Last Name</th>
                <th>Birth Date</th>
                <th>Position</th>
            </tr>
            <c:forEach var="emp" items="${employees}">
                <tr>
                    <td>${emp.uid}</td>
                    <td>${emp.firstName}</td>
                    <td>${emp.middleName}</td>
                    <td>${emp.lastName}</td>
                    <td>${emp.birthDate}</td>
                    <td>${emp.position}</td>
                </tr>
            </c:forEach>
        </table>
    </c:when>
    <c:otherwise>
        <p class="no-result"> 0 results found.</p>
    </c:otherwise>
</c:choose>

<a href="<c:url value='/'/>"> Back to Home</a>

</body>
</html>