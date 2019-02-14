package de.adesso.kicker.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping("/home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", userService.getLoggedInUser());
        modelAndView.setViewName("user/home");
        return modelAndView;
    }

    @GetMapping("/impressum")
    public ModelAndView impressum() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/impressum");
        return modelAndView;
    }

    @GetMapping("/users/{id}")
    public ModelAndView getUser(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", userService.getUserById(id));
        modelAndView.setViewName("user/profile");
        return modelAndView;
    }

    @GetMapping("/users/you")
    public ModelAndView getUserYourself() {
        ModelAndView modelAndView = new ModelAndView();
        userService.getLoggedInUser();
        modelAndView.addObject("user", userService.getLoggedInUser());
        modelAndView.setViewName("user/profile");
        return modelAndView;
    }

    @GetMapping(value = "/users/list")
    public ModelAndView showUsersSearchbar(@RequestParam(value = "search", required = false) String firstName,
            @RequestParam(value = "search", required = false) String lastName) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.addObject("search", userService.getUserByNameSearchbar(firstName, lastName));
        } catch (Exception i) {
        }
        modelAndView.setViewName("user/searchuser");
        return modelAndView;
    }
}
