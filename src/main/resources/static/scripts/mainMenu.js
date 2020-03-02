document.addEventListener("DOMContentLoaded", () => {
	
    getStartTransactionActionElement().addEventListener("click", StartTransactionActionClick);
    getViewProductActionElement().addEventListener("click", viewProductActionClick);
    getCreateEmployeeActionElement().addEventListener("click", createEmployeeActionClick);
    getSalesReportActionElement().addEventListener("click", salesReportactionClick);
    getCashierReportActionElement().addEventListener("click", cashierReportActionClick);
    getSignOutActionElement().addEventListener("click", signOutActionClickHandler);

});

function startTransactionActionClick(event) {
    const startTransactionActionElement = event.target;
    displayError('Functionality has not yet been implemented.');
}

function viewProductActionClick(event) {
    const viewProductActionElement = event.target;
    window.location.replace("/productListing");
}

function createEmployeeActionClick(event) {
    const createEmployeeActionElement = event.target;
    window.location.replace("/employeeDetail");
}

function salesReportActionClick(event) {
    const salesReportActionElement = event.target;
    displayError('Functionality has not yet been implemented.');
}
 
function cashierReportActionClick(event) {
    const cashierReportActionElement = event.target;
    displayError('Functionality has not yet been implemented.');
}

function getstartTransactionActionElement() {
    return document.getElementById("startTransactionButton");
}

function getViewProductsActionElement() {
    return document.getElementById("viewProductsButton");
}

function getCreateEmployeeActionElement() {
    return document.getElementById("createEmployeeButton");
}

function getSalesReportActionElement() {
    return document.getElementById("salesReportButton");
}

function getCashierReportActionElement() {
    return document.getElementById("cashierReportButton");
}

function getSignOutActionElement() {
    return document.getElementById("signOutImage");
}