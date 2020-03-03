package edu.uark.registerapp.controllers;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils; /////////???????/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.ActiveUserEntity;

@Controller
@RequestMapping(value = "/employeeDetail")
public class EmployeeDetailRouteController extends BaseRouteController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView start(
		@RequestParam final Map<String, String> queryParameters,
		final HttpServletRequest request
	) {
		
		// TODO: Logic to determine if the user associated with the current session
		//  is able to create an employee

		
		return new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName())
								.addObject("employee", (new Employee()).setFirstName(StringUtils.EMPTY).setLastName(StringUtils.EMPTY)); //
									//"employee", new Employee());
	}

	@RequestMapping(value = "/{employeeId}", method = RequestMethod.GET)
	public ModelAndView startWithEmployee(
		@PathVariable final UUID employeeId,
		@RequestParam final Map<String, String> queryParameters,
		final HttpServletRequest request
	) {

		final Optional<ActiveUserEntity> activeUserEntity =
			this.getCurrentUser(request);

		if (!activeUserEntity.isPresent()) {
			return this.buildInvalidSessionResponse();
		} else if (!this.isElevatedUser(activeUserEntity.get())) {
			return this.buildNoPermissionsResponse();
		}

		// TODO: Query the employee details using the request route parameter
		// TODO: Serve up the page
		return new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName())
								.addObject("employee", new Employee());


/////////new from product detail router////////////////////////////////////////////////////////////////////////////////////
		/*final ModelAndView modelAndView =
			new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName());
					
		try {
			modelAndView.addObject(
				ViewModelNames.EMPLOYEE.getValue(),
				this.employeeQuery.setProductId(employeeId).execute());
			} catch (final Exception e) {
			modelAndView.addObject(
				ViewModelNames.ERROR_MESSAGE.getValue(),
					e.getMessage());
					modelAndView.addObject(
				ViewModelNames.PRODUCT.getValue(),
					(new Employee())
					.setEmployeeId(0)
					.setManagerId(StringUtils.EMPTY)
					.setEmployeeEmployeeId(StringUtils.EMPTY));
			}
					
			return modelAndView;	*/							
	}
	// Helper methods
	private boolean activeUserExists() {
		// TODO: Helper method to determine if any active users Exist
		return true;
	}
////////////
	// Properties
	@Autowired
	private EmployeeQuery employeeQuery;

}
