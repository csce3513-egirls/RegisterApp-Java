package edu.uark.registerapp.models.api;

import org.apache.commons.lang3.StringUtils;

public class EmployeeSignIn extends ApiResponse{
    private String employee_id;
    public String getEmployee_id(){
        return this.employee_id;
    }

    public EmployeeSignIn setEmployee_id(String employee_id){
        this.employee_id = employee_id;
        return this;
    }

    private String password;
    public String getPassword(){
        return this.password;
    }

    public EmployeeSignIn setPassword(String password){
        this.password = password;
        return this;
    }

    public EmployeeSignIn(){
        super();
        this.employee_id = StringUtils.EMPTY;
        this.password = StringUtils.EMPTY;
    }

    public EmployeeSignIn(final String employee_id, final String password){
        super(false);
        this.employee_id = employee_id;
        this.password = password;
    }
}