package de.adesso.adessoKicker.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.adesso.adessoKicker.objects.User;
import de.adesso.adessoKicker.services.UserService;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller managing user
 * @author caylak
 *
 */
@RestController
public class UserController {

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {

	    this.userService = userService;
    }
	
	/**
	 * get all users
	 * @return List<User>
	 */
	@RequestMapping("/users")
	public List<User> getAllUsers() {

	    return userService.getAllUsers();
	}
	
	/**
	 * gets a single user identified by his id
	 * @param id long
	 * @return User
	 */
	@RequestMapping("/users/{id}")
	public User getOneUser(@PathVariable long id) {

	    return userService.getUserById(id);
	}

	@RequestMapping("/users/you")
	public ModelAndView getUser(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", userService.getLoggedInUser());
        modelAndView.addObject("allUsers", userService.getAllUsers());
        modelAndView.setViewName("user/_profile");
        return modelAndView;

	}
	
	/**
	 * updates users profile parameter: object and the id of the user
	 * @param user User
	 * @param id long
	 */
	@RequestMapping(method=RequestMethod.PUT, value="users/edit/{id}")
	public void updateUser(@RequestBody User user, @PathVariable long id)
	{

		userService.saveUser(user);
	}
	
	/**
	 * deletes a user identified by its id
	 * @param user User
	 * @param id long
	 */
	@RequestMapping(method=RequestMethod.DELETE, value="users/delete/{id}")
	public void deleteUser(@RequestBody User user, @PathVariable long id) {

		userService.deleteUser(id);
	}
	
}