package edu.uark.registerapp.controllers;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.commands.employees.ActiveEmployeeExistsQuery;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnauthorizedException;
import edu.uark.registerapp.controllers.enums.QueryParameterMessages;
import edu.uark.registerapp.controllers.enums.QueryParameterNames;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.api.EmployeeSignIn;
import edu.uark.registerapp.commands.employees.EmployeeQuery;

@Controller
@RequestMapping(value = "/employeeDetail")
public class EmployeeDetailRouteController extends BaseRouteController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView start(
		@RequestParam final Map<String, String> queryParameters,
        final HttpServletRequest request
	) {
		
		
		//  is able to create an employee

        final Optional<ActiveUserEntity> activeUserEntity =
            this.getCurrentUser(request);
            
       // final ActiveUserEntity activeUserEntity = this.getCurrentUser(request);
        
        ModelAndView modelAndView = this.setErrorMessageFromQueryString(new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName()), queryParameters);


        //modelAndView.addObject(
		//	ViewModelNames.IS_ELEVATED_USER.getValue(),
		//	this.isElevatedUser(activeUserEntity.get()));

       


        //TODO: I don't think the first half of this if statement is right
        try{
            if (!this.activeUserExists() || this.isElevatedUser(activeUserEntity.get())){

                return new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName())
                .addObject("employee", new Employee()); //TODO: Is this the right thing to return?
    
            }else if(!activeUserEntity.isPresent()){
                return this.buildInvalidSessionResponse();
                //System.out.println("Error: requested employee detail is not for active employee.");
                //return new ModelAndView(REDIRECT_PREPEND.concat(ViewNames.SIGN_IN.getRoute())).addObject("employeeSignIn", new EmployeeSignIn());
            }else{
                //TODO: Is this the rigth response?
                return this.buildNoPermissionsResponse();
               // System.out.println("An error occurred. Redirecting to main menu.");
               // return new ModelAndView(REDIRECT_PREPEND.concat(ViewNames.MAIN_MENU.getRoute()));
            }
        }catch (NotFoundException e){
            return new ModelAndView(
                ViewNames.EMPLOYEE_DETAIL.getViewName())
                                                    .addObject(ViewModelNames.ERROR_MESSAGE.getValue(),e.getMessage())
                                                    .addObject("employee", new Employee());
        }
        
        ///MAYBE DONE
		//return new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName())
		//						.addObject("employee", new Employee());
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

			return new ModelAndView(
                
                   ViewNames.SIGN_IN.getRoute().concat(
                      this.buildInitialQueryParameter(
                         QueryParameterNames.ERROR_CODE.getValue(),
                             QueryParameterMessages.SESSION_NOT_ACTIVE.getKeyAsString())));

		} else if (!this.isElevatedUser(activeUserEntity.get())) {
            //return this.buildNoPermissionsResponse();
          
           return new ModelAndView(
                    ViewNames.SIGN_IN.getRoute().concat(
                        this.buildInitialQueryParameter(
                            QueryParameterNames.ERROR_CODE.getValue(),
                            QueryParameterMessages.NO_PERMISSIONS_TO_VIEW.getKeyAsString())));
		}


        //Task 12 stuff
        Employee employee = employeeQuery.setEmployeeId(employeeId).execute();


       
		// TODO: Query the employee details using the request route parameter// maybe done
		// TODO: Serve up the page
		return new ModelAndView(ViewNames.EMPLOYEE_DETAIL.getViewName())
								.addObject("employee", employee);
								
	}
	// Helper methods
	private boolean activeUserExists() {

        
		// Helper method to determine if any active users Exist
		return activeEmployeeExistsQuery.execute();
    }
    

    //properties
    @Autowired
    private ActiveEmployeeExistsQuery activeEmployeeExistsQuery;

    @Autowired
    private EmployeeQuery employeeQuery;

  //  @Autowired
  //  private ValidateActiveUserCommand validateActiveUserCommand;

}
