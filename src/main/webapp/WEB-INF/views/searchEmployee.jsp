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

</body>
</html>