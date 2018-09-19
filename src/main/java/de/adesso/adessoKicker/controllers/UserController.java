package de.adesso.adessoKicker.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.adesso.adessoKicker.objects.User;
import de.adesso.adessoKicker.services.UserSearchService;

@RestController
public class UserController {

	@Autowired
	private UserSearchService userSearchService;
	
	@RequestMapping("/users")
	public List<User> getAllUsers()
	{	
		return userSearchService.getAllUsers();
	}
	
	@RequestMapping("/users/{id}")
	public User getOneUser(@PathVariable long id)
	{
		return userSearchService.getOneUser(id);
	}
	
	@RequestMapping("/users/you")
	public User getUserSelf(User user)
	{
		return userSearchService.getUserSelf(user);
		
	}
	
	//for admins
	@RequestMapping(method=RequestMethod.PUT, value="users/edit/{id}")
	public void updateUser(@RequestBody User user, @PathVariable long id)
	{
		userSearchService.updateUser(user, id);
	}
	//for admins
	@RequestMapping(method=RequestMethod.DELETE, value="users/delete/{id}")
	public void deleteUser(@RequestBody User user, @PathVariable long id)
	{
		userSearchService.deleteUser(id);
	}
	
}
