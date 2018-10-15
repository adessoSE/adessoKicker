package de.adesso.kicker.user;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
     * 
     * @param id long
     * @return User
     */
    @GetMapping("/users/{id}")
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
    @GetMapping("/users")
    public List<User> getAllUsers() {

        return userService.getAllUsers();
    }

    @GetMapping("/users/you")
    public ModelAndView getUser() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", userService.getLoggedInUser());
        modelAndView.addObject("allUsers", userService.getAllUsers());
        modelAndView.setViewName("user/_profile");
        return modelAndView;
    }

    @GetMapping(value = "users/list")
    public ModelAndView showUsersByName(@RequestParam(value = "search", required = false) String firstName,
            @RequestParam(value = "search", required = false) String lastName) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.addObject("search", userService.getUserByName(firstName, lastName));
        } catch (Exception i) {
        }
        modelAndView.setViewName("user/testsearch");
        return modelAndView;
    }

    /**
     * deletes a user identified by its id
     *
     * @param user User
     * @param id   long
     */
    @DeleteMapping("users/delete/{id}")
    public void deleteUser(@RequestBody User user, @PathVariable long id) {

        userService.deleteUser(id);
    }
}
