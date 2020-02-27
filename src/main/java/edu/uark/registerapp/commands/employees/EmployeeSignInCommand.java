package edu.uark.registerapp.commands.employees;

import org.springframework.beans.factory.annotation.Autowired;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.models.api.EmployeeSignIn;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.repositories.EmployeeRepository;

import java.util.Arrays;
import java.util.Optional;

public class EmployeeSignInCommand implements VoidCommandInterface {
    
    @Override
    public void execute() {
    // TODO Auto-generated method stub
    
    }


    private EmployeeSignIn employeeSignIn;
    private String sessionKey;

    @Autowired
    private EmployeeRepository employeeRepository;
    
    public EmployeeSignInCommand(EmployeeSignIn _employeeSignIn){
        final String tempEmployeeId = _employeeSignIn.getEmployeeID();
        final String tempEmployeePassword = _employeeSignIn.getPassword();

        if(tempEmployeeId != null && !tempEmployeeId.isEmpty() && tempEmployeeId.matches("[0-9]+")){
            employeeSignIn.setEmployeeID((tempEmployeeId));
        }

        if(tempEmployeePassword != null && !tempEmployeePassword.isEmpty()){
            employeeSignIn.setPassword(tempEmployeePassword);
        }
    }

    public boolean verifyEmployeeExists(){
        final boolean employeeExists = employeeRepository.existsByEmployeeId(Integer.parseInt(employeeSignIn.getEmployeeID()));

        if(employeeExists){
            return true;
        }
        
        return false;
    }

    public boolean verifyEmployeePassword(){
        
        
        
        //final boolean arePasswordsTheSame = Arrays.equals(employeeSignIn.getPassword().getBytes(),  employeeCheckPassword.get().getPassword()); 
       
        if(this.verifyEmployeeExists()){
            final Optional<EmployeeEntity> employeeCheckPassword = employeeRepository.findByEmployeeId(Integer.parseInt(employeeSignIn.getEmployeeID()));
            return Arrays.equals(employeeSignIn.getPassword().getBytes(),  employeeCheckPassword.get().getPassword());
        }

        return false;
    }

	

    
    


}