package edu.uark.registerapp.models.api;

public class EmployeeSignIn{
    private String employeeId;
    private String password;

    public EmployeeSignIn(String id, String password){
        this.employeeId = id;
        this.password = password;
    }

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
}