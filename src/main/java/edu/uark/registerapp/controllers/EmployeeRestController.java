package edu.uark.registerapp.controllers;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.uark.registerapp.commands.activeUsers.ValidateActiveUserCommand;
import edu.uark.registerapp.commands.employees.ActiveEmployeeExistsQuery;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.controllers.enums.QueryParameterNames;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.ApiResponse;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.enums.EmployeeClassification;

@RestController
@RequestMapping(value = "/api/employee")
public class EmployeeRestController extends BaseRestController {
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public @ResponseBody ApiResponse createEmployee(
		@RequestBody final Employee employee,
		final HttpServletRequest request,
		final HttpServletResponse response
	) {

		boolean isInitialEmployee = false;
        ApiResponse canCreateEmployeeResponse;
        
		try {
			activeEmployeeExistsQuery.execute();

            if(!EmployeeClassification.isElevatedUser(employee.getClassification())){
                canCreateEmployeeResponse =
				this.redirectUserNotElevated(request, response);
            }else{
                canCreateEmployeeResponse = new ApiResponse(); //TODO: maybe not necessary, but might not be initialized otherwise
            }
			
		} catch (final NotFoundException e) {
			isInitialEmployee = true;
			canCreateEmployeeResponse = new ApiResponse();
		}

        final Optional<ActiveUserEntity> activeUserEntity = Optional.of(
            this.validateActiveUserCommand
                .setSessionKey(request.getSession().getId())
                .execute());

        if(!activeUserEntity.isPresent()){
            canCreateEmployeeResponse = this.redirectSessionNotActive(response); //TODO: is this right
        }

		if (!canCreateEmployeeResponse.getRedirectUrl().equals(StringUtils.EMPTY)) {
			return canCreateEmployeeResponse;
		}

        // TODO: Create an employee;
        ///NEED TASK 12
		final Employee createdEmployee = new Employee();

		if (isInitialEmployee) {
			createdEmployee
				.setRedirectUrl(
					ViewNames.SIGN_IN.getRoute().concat(
						this.buildInitialQueryParameter(
							QueryParameterNames.EMPLOYEE_ID.getValue(),
							createdEmployee.getEmployeeId())));
		}

		return createdEmployee.setIsInitialEmployee(isInitialEmployee);
	}

	@RequestMapping(value = "/{employeeId}", method = RequestMethod.PATCH)
	public @ResponseBody ApiResponse updateEmployee(
		@PathVariable final UUID employeeId,
		@RequestBody final Employee employee,
		final HttpServletRequest request,
		final HttpServletResponse response
	) {


        final Optional<ActiveUserEntity> activeUserEntity = Optional.of(
            this.validateActiveUserCommand
                .setSessionKey(request.getSession().getId())
                .execute());

        if(!activeUserEntity.isPresent()){
            return this.redirectSessionNotActive(response);
        }

		final ApiResponse elevatedUserResponse =
			this.redirectUserNotElevated(request, response);
		if (!elevatedUserResponse.getRedirectUrl().equals(StringUtils.EMPTY)) {
			return elevatedUserResponse; //TODO: I think this is already done?
		}

        ///NEED TASK 12 FOR THIS
		// TODO: Update the employee
		return employee;
    }
    

    //properties
    @Autowired
    private ActiveEmployeeExistsQuery activeEmployeeExistsQuery;
    @Autowired
    private ValidateActiveUserCommand validateActiveUserCommand;
}
