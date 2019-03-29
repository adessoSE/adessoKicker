package de.adesso.kicker.site.controller;

import de.adesso.kicker.user.exception.UserNotFoundException;
import de.adesso.kicker.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class HomeController {

    private final UserService userService;

    @GetMapping(value = { "/", "/home", "/ranking" })
    public String ranking(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size,
            Model model) {
        var users = userService.getUserPageSortedByRating(page, size);
        var allUsers = userService.getAllUsersWithStatistics();
        try {
            var user = userService.getLoggedInUser();
            model.addAttribute("user", user);
        } catch (UserNotFoundException e) {
            model.addAttribute("userFound", false);
        }
        model.addAttribute("users", users);
        model.addAttribute("allUsers", allUsers);
        return "sites/ranking.html";
    }
}
