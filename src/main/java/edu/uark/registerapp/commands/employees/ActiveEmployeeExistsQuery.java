package edu.uark.registerapp.commands.employees;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.repositories.EmployeeRepository;

public class ActiveEmployeeExistsQuery implements ResultCommandInterface<Boolean> {

    //EmployeeEntity employeeEntity = new EmployeeEntity();
    //EmployeeRepository employeeRepository = new EmployeeRepository(employeeEntity, java.util.UUID);
    


    @Override
    public Boolean execute() {
        final Boolean activeEmployee = this.employeeRepository.existsByIsActive(true);
        if(activeEmployee != Boolean.TRUE) {
            throw new NotFoundException("No active users found.");
        }
        return Boolean.TRUE;
    }
    
/*
    public boolean ActiveEmployeeExistsQuery() {

        //TODO: Check to make sure this is working correctly (Professor says it works)
        final boolean activeEmployeeExists = employeeRepository.existsByIsActive(true);

        if(!activeEmployeeExists)
        {
            
            
        }
        return true;
    }
*/
    
    @Autowired
    private EmployeeRepository employeeRepository;
}