package de.adesso.kicker.user;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * RestController "UserController" that manages everything related with users.
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
     * getLoggedInUser() gets the current user.
     * @return ModelAndView
     */
    @GetMapping(value={"", "/", "home"})
    public ModelAndView getLoggedInUser() {
    	
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.addObject("user", userService.getLoggedInUser());
    	modelAndView.setViewName("user/home");
    	return modelAndView;
    }

    /**
     * getUser() gets an unique user identified by an index.
     * @param id long
     * @return ModelAndView
     */
    @GetMapping("/users/{id}")
    public ModelAndView getUser(@PathVariable long id) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", userService.getUserById(id));
        modelAndView.addObject("allUsers", userService.getAllUsers());
        modelAndView.setViewName("user/_profile");
        return modelAndView;
    }

    /**
     * getAllUsers() gets all users that are in the database.
     * @return ModelAndView
     */
    @GetMapping("/users")
    public List<User> getAllUsers() {

        return userService.getAllUsers();
    }

    /**
     * getUserYourself() gets the logged in user.
     * @return
     */
    @GetMapping("/users/you")
    public ModelAndView getUserYourself() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", userService.getLoggedInUser());
        modelAndView.addObject("allUsers", userService.getAllUsers());
        modelAndView.setViewName("user/_profile");
        return modelAndView;
    }

    /**
     * showUsersSearchbar() finds teams by the same teamName ignoring the case or something similar to it.
     * @param firstName
     * @param lastName
     * @return
     */
    @GetMapping(value = "users/list")
    public ModelAndView showUsersSearchbar(@RequestParam(value = "search", required = false) String firstName,
            @RequestParam(value = "search", required = false) String lastName) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.addObject("search", userService.getUserByNameSearchbar(firstName, lastName));
        } catch (Exception i) {
        }
        modelAndView.setViewName("user/testsearch");
        return modelAndView;
    }

    /**
     * deleteUser() deletes an unique user identified by an index.
     * @param id long
     */
    @DeleteMapping("users/delete/{id}")
    public void deleteUser(@RequestBody User user, @PathVariable long id) {

        userService.deleteUser(id);
    }
}
