<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>All Employees</title>
</head>
<body>
<h2> List of Employees</h2>

<input type="text" id="filterBox" placeholder="🔍 Filter by name or position..." onkeyup="filterTable()" />

<c:if test="${not empty employees}">
    <table id="employeeTable">
        <thead>
            <tr>
                <th>ID</th>
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
                    <td>${emp.id}</td>
                    <td>${emp.firstName}</td>
                    <td>${emp.middleName}</td>
                    <td>${emp.lastName}</td>
                    <td>${emp.birthDate}</td>
                    <td>${emp.position}</td>
                    <td>
                        <a class="btn edit-btn" href="edit?id=${emp.id}"> Edit</a>
<%--                         <a class="btn delete-btn" href="delete?uid=${emp.uid}" onclick="return confirm('Are you sure you want to delete this employee?');">🗑️ Delete</a> --%>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</c:if>

<c:if test="${empty employees}">
    <div class="no-results">😢 No employees found.</div>
</c:if>
<a href="<c:url value='/'/>" class="back-link">⬅ Back to Home</a>

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

        const noResultsMsg = document.querySelector(".no-results");
        if (noResultsMsg) {
            noResultsMsg.style.display = visibleCount === 0 ? "block" : "none";
        }
    }
</script>


</body>
</html>