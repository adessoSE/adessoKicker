package de.adesso.kicker.user;

import java.security.Principal;

import org.keycloak.KeycloakPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
     *
     * @return ModelAndView
     */
    @GetMapping(value = { "", "/", "home" })
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
//        User user = userService.getLoggedInUser();
//        modelAndView.addObject("user", user);
        modelAndView.setViewName("user/home");
        return modelAndView;
    }

    @GetMapping(value = "/impressum")
    public ModelAndView impressum() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/impressum");
        return modelAndView;
    }

    /**
     * getUser() gets an unique user identified by an index.
     *
     * @param id long
     * @return ModelAndView
     */
    @GetMapping("/users/{id}")
    public ModelAndView getUser(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", userService.getUserById(id));
        modelAndView.setViewName("user/_profile");
        return modelAndView;
    }

    /**
     * getUserYourself() gets the logged in user.
     *
     * @return ModelAndView
     */
    @GetMapping("/users/you")
    public ModelAndView getUserYourself(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        userService.getLoggedInUser();
        modelAndView.addObject("user", principal);
        modelAndView.setViewName("user/_profile");
        return modelAndView;
    }

    /**
     * showUsersSearchbar() finds teams by the same teamName ignoring the case or
     * something similar to it.
     *
     * @param firstName
     * @param lastName
     * @return
     */
//    @GetMapping(value = "/users/list")
//    public ModelAndView showUsersSearchbar(@RequestParam(value = "search", required = false) String firstName,
//            @RequestParam(value = "search", required = false) String lastName) {
//        ModelAndView modelAndView = new ModelAndView();
//        try {
//            modelAndView.addObject("search", userService.getUserByNameSearchbar(firstName, lastName));
//        } catch (Exception i) {
//        }
//        modelAndView.setViewName("user/searchuser");
//        return modelAndView;
//    }
}
