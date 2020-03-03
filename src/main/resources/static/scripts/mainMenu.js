Packages.edu.uark.registerapp.models.enums.EmployeeClassification;

function startTransaction() {
	document.getElementById("transaction").innerHTML = "Functionality has not yet been implemented."; 
}
		
function viewProducts() {
			
}

function createEmployee() {
	var classification = EmployeeClassification.getClassification;
	if(classification == "Shift Manager" || classification == "General Manager") {
		document.getElementById("createEmployee").style.visibility = 'visible';
	}
}

function salesReport() {
	document.getElementById("sales").innerHTML = "Functionality has not yet been implemented."; 
	if(classification == "Shift Manager" || classification == "General Manager") {
		document.getElementById("saleReport").style.visibility = 'visible';
    }
}

function cashierReport() {
    document.getElementById("cashier").innerHTML = "Functionality has not yet been implemented."; 
	if(classification == "Shift Manager" || classification == "General Manager") {
		document.getElementById("cashierReport").style.visibility = 'visible';
	}
}

function signOut() {
	alert("sign out");
}