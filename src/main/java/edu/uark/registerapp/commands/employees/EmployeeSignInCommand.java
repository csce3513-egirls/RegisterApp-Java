package edu.uark.registerapp.commands.employees;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.commons.lang3.StringUtils;

import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnauthorizedException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.api.EmployeeSignIn;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.repositories.ActiveUserRepository;
import edu.uark.registerapp.models.repositories.EmployeeRepository;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeSignInCommand implements VoidCommandInterface {
    @Transactional
    @Override
    public void execute() {
        this.validateProperties();

        final Optional<EmployeeEntity> employeeEntity = this.employeeRepository.findByEmployeeId(Integer.parseInt(this.employeeSignIn.getEmployee_id()));
       
        if(!employeeEntity.isPresent()){
            throw new NotFoundException("Employee");
        }
        
        
        if(!Arrays.equals(employeeSignIn.getPassword().getBytes(),  employeeEntity.get().getPassword())){
            throw new UnauthorizedException();//TODO: is this the right exception to use?
        }

        final Optional<ActiveUserEntity> activeUserEntity = this.activeUserRepository.findByEmployeeId(employeeEntity.get().getId());

        //is there an active user matching incoming user present?
        if(activeUserEntity.isPresent()){
            activeUserEntity.get().setSessionKey(this.sessionKey);
            this.activeUserRepository.save(activeUserEntity.get()); //TODO: make sure return of employeeSignIn not required (see ProductUpdateCommand.java)
        }else
        {
            activeUserEntity.get().setSessionKey(this.sessionKey);
            activeUserEntity.get().setEmployeeId(employeeEntity.get().getId());
            activeUserEntity.get().setName(employeeEntity.get().getFirstName() + employeeEntity.get().getLastName()); //TODO: make sure this is correctly formatted for name
            activeUserEntity.get().setClassification(employeeEntity.get().getClassification());
            this.activeUserRepository.save(activeUserEntity.get());//TODO: maybe move this from this and previous if statement outside, will run no matter what
        }
        

        //TODO: return necessary?
    }



    private void validateProperties(){
        if(StringUtils.isBlank(this.employeeSignIn.getEmployee_id())){
            throw new UnprocessableEntityException("EmployeeSignIn: blank name");
        }

        if(!this.employeeSignIn.getEmployee_id().matches("[0-9]+")){
            throw new UnprocessableEntityException("EmployeeSignIn: non-numeric employee id");
        }

        if(StringUtils.isBlank(this.employeeSignIn.getPassword())){
            throw new UnprocessableEntityException("EmployeeSignIn: blank password");
        }
    }

    public EmployeeSignInCommand setSessionKey(final String _sessionKey){
        this.sessionKey = _sessionKey;
        return this;
    }

    public EmployeeSignIn getEmployeeSignIn(){
        return this.employeeSignIn;
    }

    public EmployeeSignInCommand setEmployeeSignIn(final EmployeeSignIn _employeeSignIn){
        this.employeeSignIn = _employeeSignIn;
        return this;
    }


    

    //properties
    private EmployeeSignIn employeeSignIn;
    private String sessionKey;

    @Autowired
    private EmployeeRepository employeeRepository;
    private ActiveUserRepository activeUserRepository;
    
    

}