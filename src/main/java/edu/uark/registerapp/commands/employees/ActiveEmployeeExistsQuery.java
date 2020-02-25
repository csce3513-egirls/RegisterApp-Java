package edu.uark.registerapp.commands.employees;

import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.models.repositories.EmployeeRepository;

public class ActiveEmployeeExistsQuery {
    private EmployeeRepository employeeRepository = new EmployeeRepository();

    public static boolean activeEmployeeExists(){
            boolean exists = employ.existsByIsActive(true);
        if (!EmployeeRepository.existsByIsActive())
        {
            throw new NotFoundException("No active employees found");
        }else
        {
            
        }
        
    }


}