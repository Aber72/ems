<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Search Employees</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f5f7fa;
            padding: 30px;
        }
        h2 {
            text-align: center;
            color: #3e1d25;
            margin-bottom: 30px;
        }
        #filterBox {
            width: 100%;
            max-width: 400px;
            margin: 0 auto 20px auto;
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 5px;
            display: block;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        th, td {
            text-align: center;
            padding: 12px;
            border-bottom: 1px solid #e0e0e0;
        }
        th {
            background-color: #3e1d25;
            color: white;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
        .btn {
            padding: 6px 12px;
            border-radius: 4px;
            color: white;
            text-decoration: none;
            font-weight: bold;
            margin-right: 5px;
        }
        .edit-btn { background-color: #1e88e5; }
        .edit-btn:hover { background-color: #1565c0; }
        .delete-btn { background-color: #e53935; }
        .delete-btn:hover { background-color: #b71c1c; }
        .no-results {
            text-align: center;
            color: #e53935;
            font-style: italic;
            margin-top: 20px;
            display: none;
        }
        .back-link {
            display: block;
            text-align: center;
            margin-top: 30px;
            font-weight: bold;
            color: #1565c0;
        }
        .back-link:hover {
            text-decoration: underline;
        }
        .debug-count {
            text-align: center;
            color: #333;
            font-size: 14px;
            margin-bottom: 10px;
        }
    </style>
</head>
<body>

<h2>Search Employee Results</h2>

<input type="text" id="filterBox" placeholder="🔍 Filter by name or position..." onkeyup="filterTable()" />

<div class="debug-count">
    Employees found (from server): ${fn:length(employees)}
</div>

<table id="employeeTable">
    <thead>
        <tr>
            <th>UID</th>
            <th>First Name</th>
            <th>Middle Name</th>
            <th>Last Name</th>
            <th>Birth Date</th>
            <th>Position</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="emp" items="${employees}">
            <tr>
                <td>${emp.uid}</td>
                <td>${emp.firstName}</td>
                <td>${emp.middleName}</td>
                <td>${emp.lastName}</td>
                <td>${emp.birthDate}</td>
                <td>${emp.position}</td>
                <td>
                    <a class="btn edit-btn" href="edit?uid=${emp.uid}">Edit</a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<div class="no-results" id="noResults">😢 No matching employees found.</div>

<a href='/'class="back-link">⬅ Back to Home</a>

<script type="text/javascript">
    function filterTable() {
        const input = document.getElementById("filterBox");
        const filter = input.value.toLowerCase();
        const table = document.getElementById("employeeTable");
        const rows = table.getElementsByTagName("tr");
        let visibleCount = 0;

        for (let i = 1; i < rows.length; i++) {
            const row = rows[i];
            const cells = row.getElementsByTagName("td");
            let match = false;

            for (let j = 1; j < cells.length - 1; j++) {
                if (cells[j].innerText.toLowerCase().includes(filter)) {
                    match = true;
                    break;
                }
            }

            row.style.display = match ? "" : "none";
            if (match) visibleCount++;
        }

        document.getElementById("noResults").style.display = visibleCount === 0 ? "block" : "none";
    }
</script>

</body>
</html>
