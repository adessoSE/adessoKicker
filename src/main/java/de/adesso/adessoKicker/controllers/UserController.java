package de.adesso.adessoKicker.controllers;

import de.adesso.adessoKicker.objects.User;
import de.adesso.adessoKicker.services.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller managing user
 *
 * @author caylak
 */
@RestController
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }

	/**
	 * gets a single user identified by his id
	 * @param id long
	 * @return User
	 */
	@RequestMapping("/users/{id}")
	public ModelAndView getOneUser(@PathVariable long id) {

		ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", userService.getUserById(id));
        modelAndView.addObject("allUsers", userService.getAllUsers());
        modelAndView.setViewName("user/_profile");
        return modelAndView;
	}

    /**
     * get all users
     *
     * @return List<User>
     */
    @RequestMapping("/users")
    public List<User> getAllUsers() {

        return userService.getAllUsers();
    }

    @RequestMapping("/users/you")
    public ModelAndView getUser() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", userService.getLoggedInUser());
        modelAndView.addObject("allUsers", userService.getAllUsers());
        modelAndView.setViewName("user/_profile");
        return modelAndView;
    }

    /**
     * updates users profile parameter: object and the id of the user
     *
     * @param user User
     * @param id   long
     */
    @RequestMapping(method = RequestMethod.PUT, value = "users/edit/{id}")
    public void updateUser(@RequestBody User user, @PathVariable long id) {

        userService.saveUser(user);
    }

    /**
     * deletes a user identified by its id
     *
     * @param user User
     * @param id   long
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "users/delete/{id}")
    public void deleteUser(@RequestBody User user, @PathVariable long id) {

        userService.deleteUser(id);
    }
}
