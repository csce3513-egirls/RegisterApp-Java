package edu.uark.registerapp.commands.activeUsers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.apache.commons.lang3.StringUtils;
import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.VoidCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnauthorizedException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.entities.EmployeeEntity;
import edu.uark.registerapp.models.repositories.ActiveUserRepository;
import edu.uark.registerapp.models.repositories.EmployeeRepository;

@Service
public class ActiveUserDeleteCommand implements VoidCommandInterface {
    @Transactional
	@Override
	public void execute() {
		final Optional<ActiveUserEntity> activeUserEntity =
            this.activeUserRepository.findBySessionKey(this.sessionKey);
            
        if (!activeUserEntity.isPresent()) {
            throw new UnauthorizedException();
        }

        final Optional<EmployeeEntity> employeeEntity = this.employeeRepository.findById(activeUserEntity.get().getEmployeeId());

        if (!employeeEntity.isPresent()){
            throw new NotFoundException("Employee Entity");
        }

        if(StringUtils.isBlank(employeeEntity.get().getFirstName()) || StringUtils.isBlank(employeeEntity.get().getLastName())){
            throw new UnprocessableEntityException("Employee Entity: name is blank");
        }
        

        this.activeUserRepository.delete(activeUserEntity.get());

		//TODO: this doesn't seem right, need to check functionality

		
	}

	// Properties
	private String sessionKey;


    

	public String getSessionKey() {
		return this.sessionKey;
	}

	public ActiveUserDeleteCommand setSessionKey(final String sessionKey) {
		this.sessionKey = sessionKey;
		return this;
	}

	@Autowired
    private ActiveUserRepository activeUserRepository;
    private EmployeeRepository employeeRepository;
}
