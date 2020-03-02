package edu.uark.registerapp.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;

import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
//import edu.uark.registerapp.models.api.Employee;
import edu.uark.registerapp.models.api.EmployeeSignIn;
import edu.uark.registerapp.commands.employees.ActiveEmployeeExistsQuery;
import edu.uark.registerapp.commands.employees.EmployeeSignInCommand;

@Controller
@RequestMapping(value = "/")
public class SignInRouteController extends BaseRouteController {
	// TODO: Route for initial page load DONE
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView requestDocument(@RequestParam Map<String,String> allParams) {
        try{
			query.execute();
        }
        catch(Exception e){
            System.out.println("An error ocurred: " + e + "\n");
            return new ModelAndView(
			REDIRECT_PREPEND.concat(
			ViewNames.EMPLOYEE_DETAIL.getRoute()))
												.addObject(ViewModelNames.ERROR_MESSAGE.getValue(),e.getMessage());
        }      
        return new ModelAndView(ViewNames.SIGN_IN.getRoute());
    }

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ModelAndView performSignIn(
		// TODO: Define an object that will represent the sign in request and add it as a parameter here DONE
		EmployeeSignIn employee,
		HttpServletRequest request
	) {

		// TODO: Use the credentials provided in the request body
		//  and the "id" property of the (HttpServletRequest)request.getSession() variable
		//  to sign in the user DONE
		try{
			HttpSession session = request.getSession();
			String session_id = session.getId();
			this.command.setEmployeeSignIn(employee);
			this.command.setSessionKey(session_id);
			this.command.execute();
		}

		catch(Exception e){
			System.out.println("An error ocurred: " + e + "\n");
			return new ModelAndView(REDIRECT_PREPEND.concat(ViewNames.SIGN_IN.getRoute()))
									.addObject(ViewModelNames.ERROR_MESSAGE.getValue(),e.getMessage());
		}

		return new ModelAndView(
			REDIRECT_PREPEND.concat(
				ViewNames.MAIN_MENU.getRoute()))
									.addObject(ViewModelNames.IS_ELEVATED_USER.getValue(),true);
	}

	@Autowired
	private EmployeeSignInCommand command;
	@Autowired
	private ActiveEmployeeExistsQuery query;

}
