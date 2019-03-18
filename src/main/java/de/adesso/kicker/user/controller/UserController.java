package de.adesso.kicker.user.controller;

import de.adesso.kicker.user.persistence.User;
import de.adesso.kicker.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/u/{id}")
    public ModelAndView getUserProfile(@PathVariable String id) {
        var modelAndView = new ModelAndView();
        var user = userService.getUserById(id);
        return defaultProfileView(modelAndView, user);
    }

    @GetMapping("/you")
    public ModelAndView getOwnProfile() {
        var modelAndView = new ModelAndView();
        var user = userService.getLoggedInUser();
        return defaultProfileView(modelAndView, user);
    }

    private ModelAndView defaultProfileView(ModelAndView modelAndView, User user) {
        modelAndView.addObject("user", user);
        modelAndView.setViewName("sites/profile.html");
        return modelAndView;
    }
}
