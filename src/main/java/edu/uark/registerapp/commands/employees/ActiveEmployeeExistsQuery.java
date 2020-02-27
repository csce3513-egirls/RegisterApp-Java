package edu.uark.registerapp.commands.employees;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.repositories.EmployeeRepository;

public class ActiveEmployeeExistsQuery {

    //EmployeeEntity employeeEntity = new EmployeeEntity();
    //EmployeeRepository employeeRepository = new EmployeeRepository(employeeEntity, java.util.UUID);
    
    @Autowired
    private EmployeeRepository employeeRepository;

    public boolean activeEmployeeExistsQuery() {

        //TODO: Check to make sure this is working correctly
        final boolean activeEmployeeExists = employeeRepository.existsByIsActive(true);

        if(!activeEmployeeExists)
        {
            throw new NotFoundException("No active users found.");
            
        }
        return true;
    }

}