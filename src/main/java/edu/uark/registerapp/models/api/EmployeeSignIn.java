package edu.uark.registerapp.models.api;

public class EmployeeSignIn extends ApiResponse{
    private String employeeId;
    private String password;

    public String getEmployeeID(){
        return this.employeeId;
    }

    public String getPassword(){
        return this.password;
    }

    public void setEmployeeID(String new_ID){
        this.employeeId = new_ID;
    }

    public void setPassword(String new_pass){
        this.password = new_pass;
    }

    public EmployeeSignIn(){
        super();
        this.employeeId = "";
        this.password = "";
    }

    public EmployeeSignIn(final String id, final String password){
        super(false);
        this.employeeId = id;
        this.password = password;
    }
}