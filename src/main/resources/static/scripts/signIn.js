document.addEventListener("DOMContentLoaded", function(event) {
	// TODO: Anything you want to do when the page is loaded?
	
});

function validateForm() {
	// TODO: Validate the user input DONE
	var emp_id = document.forms["login"]["employee_id"].value;
	var pass = document.forms["login"]["password"].value;
	var employee = new EmployeeSignIn(emp_id, pass);
	var check = new EmployeeSignInCommand(employee);
	if(check == true){
		return true;
	}
	else{
		return false;
	}
}
