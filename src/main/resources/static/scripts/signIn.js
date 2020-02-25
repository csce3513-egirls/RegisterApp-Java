document.addEventListener("DOMContentLoaded", function(event) {
	// TODO: Anything you want to do when the page is loaded?
	// const signInElement = document.getElementById();
});

function validateForm() {
	// TODO: Validate the user input DONE
	var emp_id = document.forms["login"]["employee_id"].value;
	var pass = document.forms["login"]["password"].value;
	if (emp_id == "" || pass == ""){
		return false;
	}
	else{
		return true;
	}
}
