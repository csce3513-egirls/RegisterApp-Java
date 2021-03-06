let hideEmployeeSavedAlertTimer = undefined;

document.addEventListener("DOMContentLoaded", () => {
	// TODO: Things that need doing when the view is loaded
	const employeeIdElement = getEmployeeIdElement();
	getSaveActionElement().addEventListener("click", saveActionClick);

	if (!employeeIdElement.disabled) {
		employeeIdElement.focus();
		employeeIdElement.select();
	}
});

// Save
function saveActionClick(event) {
	// TODO: Actually save the employee via an AJAX call
	//displayEmployeeSavedAlertModal();

	if (!validateSave()) {
		return;
	}

	const saveActionElement = event.target;
	saveActionElement.disabled = true;

	const employeeId = getEmployeeId();
	const employeeIdIsDefined = ((employeeId != null) && (employeeId.trim() !== ""));
	const saveActionUrl = ("/api/employee/"
		+ (employeeIdIsDefined ? employeeId : ""));
	const saveEmployeeRequest = {
		id: employeeId,
		managerId: getEmployeeManagerId(),
		employeeId: getEmployeeEmployeeId(),
		firstName: getEmployeeFirstName(),
		lastName: getEmployeeLastName(),
		password : getEmployeePassword(),
		reCheckPassword: getEmployeeReCheckPassword(),
		type: getEmployeeType()
	};

	if (employeeIdIsDefined) {
		ajaxPut(saveActionUrl, saveEmployeeRequest, (callbackResponse) => {
			saveActionElement.disabled = false;

			if (isSuccessResponse(callbackResponse)) {
				displayEmployeeSavedAlertModal();
			}
		});
	} else {
		ajaxPost(saveActionUrl, saveEmployeeRequest, (callbackResponse) => {
			saveActionElement.disabled = false;

			if (isSuccessResponse(callbackResponse)) {
				displayEmployeeSavedAlertModal();
				if ((callbackResponse.data != null)
					&& (callbackResponse.data.id != null)
					&& (callbackResponse.data.id.trim() !== "")) {

					document.getElementById("deleteActionContainer").classList.remove("hidden");

					setEmployeeId(callbackResponse.data.id.trim());
				}
			}
		});
	}
};

function displayEmployeeSavedAlertModal() {
	if (hideEmployeeSavedAlertTimer) {
		clearTimeout(hideEmployeeSavedAlertTimer);
	}

	const savedAlertModalElement = getSavedAlertModalElement();
	savedAlertModalElement.style.display = "none";
	savedAlertModalElement.style.display = "block";

	hideEmployeeSavedAlertTimer = setTimeout(hideEmployeeSavedAlertModal, 1200);
}

function hideEmployeeSavedAlertModal() {
	if (hideEmployeeSavedAlertTimer) {
		clearTimeout(hideEmployeeSavedAlertTimer);
	}

	getSavedAlertModalElement().style.display = "none";
}

// Save
function validateSave() {
	const firstName = getEmployeeFirstName();
	if ((firstName == null) || (firstName.trim() === "")) {
		displayError("Please provide a valid first name.");
		return false;
	}

	const lastName = getEmployeeLastName();
	if ((lastName == null) || (lastName.trim() === "")) {
		displayError("Please provide a valid last name.");
		return false;
	}

	const password = getEmployeePassword();
	if ((password == null) || (password.trim() === "")) {
		displayError("Please provide a valid password.");
		return false;
	} else if (password != getEmployeeReCheckPassword()) {
		displayError("Passwords must match.");
		return false;
	}

	const type = getEmployeeType();
	if ((type == null) || (type.trim() === "")) {
		displayError("Please select a valid employee type.");
		return false;
	}

	return true;
}
// End save

//Getters and setters
function getSaveActionElement() {
	return document.getElementById("saveButton");
}

function getSavedAlertModalElement() {
	return document.getElementById("employeeSavedAlertModal");
}

function getEmployeeId() {
	return getEmployeeIdElement().value;
}
function setEmployeeId(employeeId) {
	getEmployeeElement().value = employeeId;
}
function getEmployeeIdElement() {
	return document.getElementById("employeeId");
}

function getEmployeeEmployeeId() {
	return getEmployeeEmployeeIdElement().value;
}
function getEmployeeEmployeeIdElement() {
	return document.getElementById("employeeEmployeeId");
}

function getEmployeeManagerId() {
	return getEmployeeManagerIdElement().value;
}
function getEmployeeManagerIdElement() {
	return document.getElementById("employeeManagerId");
}

function getEmployeeFirstName() {
	return getEmployeeFirstNameElement().value;
}
function getEmployeeFirstNameElement() {
	return document.getElementById("employeeFirstName");
}

function getEmployeeLastName() {
	return getEmployeeLastNameElement().value;
}
function getEmployeeLastNameElement() {
	return document.getElementById("employeeLastName");
}

function getEmployeePassword() {
	return getEmployeePasswordElement().value;
}
function getEmployeePasswordElement() {
	return document.getElementById("employeePassword");
}

function getEmployeeReCheckPassword() {
	return getEmployeeReCheckPasswordElement().value;
}
function getEmployeeReCheckPasswordElement() {
	return document.getElementById("employeeReCheckPassword");
}

function getEmployeeType() {
	return getEmployeeTypeElement().value;
}
function getEmployeeTypeElement() {
	return document.getElementById("employeeType");
}
//End getters and setters