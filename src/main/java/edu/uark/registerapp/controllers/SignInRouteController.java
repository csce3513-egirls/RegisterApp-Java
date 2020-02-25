package edu.uark.registerapp.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;

import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.EmployeeSignIn;
import edu.uark.registerapp.models.entities.ActiveUserEntity;

@Controller
@RequestMapping(value = "/")
public class SignInRouteController extends BaseRouteController {
	// TODO: Route for initial page load
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView requestDocument(@RequestParam Map<String,String> allParams) {
        try{
            // make use of functionality built in task 5
            // if employees exist, proceed to sign in
            // if no employees exist, exception is caught and user is redirected to the employee detail view/document

			// ActiveEmployeeExistsQuery query = new ActiveEmployeeExistsQuery();
        }
        catch(Exception e){
            System.out.println("An error ocurred: " + e + "\n");
            return new ModelAndView(
			REDIRECT_PREPEND.concat(
			ViewNames.EMPLOYEE_DETAIL.getRoute()));
        }      
        return new ModelAndView(REDIRECT_PREPEND.concat(ViewNames.SIGN_IN.getRoute()));
    }

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ModelAndView performSignIn(
		// TODO: Define an object that will represent the sign in request and add it as a parameter here
		EmployeeSignIn employee,
		HttpServletRequest request
	) {

		// TODO: Use the credentials provided in the request body
		//  and the "id" property of the (HttpServletRequest)request.getSession() variable
		//  to sign in the user
		try{
			HttpSession session = request.getSession();
			String session_id = session.getId();
			// EmployeeSignInCommand command = new EmployeeSignInCommand(employee, session_id);
			// command.validate();
			// command.createTable();
		}
		catch(Exception e){
			System.out.println("An error ocurred: " + e + "\n");
			return new ModelAndView(REDIRECT_PREPEND.concat(ViewNames.SIGN_IN.getRoute()));
		}

		return new ModelAndView(
			REDIRECT_PREPEND.concat(
				ViewNames.MAIN_MENU.getRoute()));
	}
}
