<script>
function validateEmployeeForm() {
    let firstName = document.forms["employeeForm"]["firstName"].value.trim();
    let lastName = document.forms["employeeForm"]["lastName"].value.trim();
    let position = document.forms["employeeForm"]["position"].value.trim();
    let birthDate = document.forms["employeeForm"]["birthDate"].value;

    let errorMsg = "";

    if (firstName === "") {
        errorMsg += "First Name is required.\n";
    }
    if (lastName === "") {
        errorMsg += "Last Name is required.\n";
    }
    if (position === "") {
        errorMsg += "Position is required.\n";
    }
    if (birthDate === "") {
        errorMsg += "Birth Date is required.\n";
    } else {
        let birth = new Date(birthDate);
        let today = new Date();
        if (birth > today) {
            errorMsg += "Birth Date cannot be in the future.\n";
        }
    }

    if (errorMsg !== "") {
        alert(errorMsg);
        return false;
    }
    return true;
}

function validateCompensationForm() {
    let type = document.forms["compensationForm"]["type"].value.trim();
    let amount = document.forms["compensationForm"]["amount"].value.trim();
    let compDate = document.forms["compensationForm"]["compDate"].value.trim();

    let errorMsg = "";

    if (type === "") {
        errorMsg += "Compensation type is required.\n";
    }
    if (amount === "") {
        errorMsg += "Amount is required.\n";
    } else if (isNaN(amount) || Number(amount) < 0) {
        errorMsg += "Amount must be a valid positive number.\n";
    }
    if (compDate === "") {
        errorMsg += "Compensation date is required.\n";
    }

    if (errorMsg !== "") {
        alert(errorMsg);
        return false;
    }
    return true;
}
</script>
