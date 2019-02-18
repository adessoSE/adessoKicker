package de.adesso.kicker.user;

import de.adesso.kicker.user.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ModelAndView getUser(@PathVariable String id) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.addObject("user", userService.getUserById(id));
        } catch (UserNotFoundException u) {
            modelAndView.setViewName("error/404.html");
            return modelAndView;
        }
        modelAndView.setViewName("user/profile");
        return modelAndView;
    }

    @GetMapping("/you")
    public ModelAndView getUserYourself() {
        ModelAndView modelAndView = new ModelAndView();
        userService.getLoggedInUser();
        modelAndView.addObject("user", userService.getLoggedInUser());
        modelAndView.setViewName("user/profile");
        return modelAndView;
    }

    @GetMapping("/list")
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
