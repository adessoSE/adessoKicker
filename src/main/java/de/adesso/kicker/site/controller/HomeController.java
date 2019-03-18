package de.adesso.kicker.site.controller;

import de.adesso.kicker.user.exception.UserNotFoundException;
import de.adesso.kicker.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final UserService userService;

    @GetMapping(value = { "/", "/home", "/ranking" })
    public ModelAndView ranking(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        ModelAndView modelAndView = new ModelAndView();
        var users = userService.getUserPageSortedByRating(page, size);
        var allUsers = userService.getAllUsersWithStatistics();
        try {
            var user = userService.getLoggedInUser();
            modelAndView.addObject("user", user);
        } catch (UserNotFoundException e) {
            modelAndView.addObject("user", false);
        }
        modelAndView.addObject("users", users);
        modelAndView.addObject("allUsers", allUsers);
        modelAndView.setViewName("sites/ranking.html");
        return modelAndView;
    }
}
