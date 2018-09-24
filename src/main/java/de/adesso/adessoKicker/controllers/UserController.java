package de.adesso.adessoKicker.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.adesso.adessoKicker.objects.User;
import de.adesso.adessoKicker.services.UserService;

/**
 * Controller managing user
 * @author caylak
 *
 */
@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	/**
	 * get all users
	 * @return
	 */
	@RequestMapping("/users")
	public List<User> getAllUsers() {

	    return userService.getAllUsers();
	}
	
	/**
	 * gets a single user identified by his id
	 * @param id
	 * @return
	 */
	@RequestMapping("/users/{id}")
	public User getOneUser(@PathVariable long id) {

	    return userService.getUserById(id);
	}
	
	/**
	 * gets the user thats currently logged in 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * NOTE:NOT WORKING YET
	 * @param id
	 * @return
	 */
	@RequestMapping("/users/you")
	public User getUser(long id)
	{
		return userService.getUserById(id);
	}
	
	/**
	 * updates users profile parameter: object and the id of the user
	 * @param user
	 * @param id
	 */
	@RequestMapping(method=RequestMethod.PUT, value="users/edit/{id}")
	public void updateUser(@RequestBody User user, @PathVariable long id)
	{

		userService.saveUser(user);
	}
	
	/**
	 * deletes a user identified by its id
	 * @param user
	 * @param id
	 */
	@RequestMapping(method=RequestMethod.DELETE, value="users/delete/{id}")
	public void deleteUser(@RequestBody User user, @PathVariable long id) {

		userService.deleteUser(id);
	}
	
}