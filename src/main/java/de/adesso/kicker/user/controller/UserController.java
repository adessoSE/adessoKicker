package de.adesso.kicker.user.controller;

import de.adesso.kicker.user.persistence.User;
import de.adesso.kicker.user.service.UserService;
import de.adesso.kicker.user.trackedstatistic.service.TrackedStatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final TrackedStatisticService trackedStatisticService;

    @GetMapping("/u/{id}")
    public String getUserProfile(@PathVariable String id, Model model) {
        var user = userService.getUserById(id);
        defaultProfileView(model, user);
        return "sites/profile.html";
    }

    @GetMapping("/you")
    public String getOwnProfile(Model model) {
        var user = userService.getLoggedInUser();
        defaultProfileView(model, user);
        return "sites/profile.html";
    }

    private void defaultProfileView(Model model, User user) {
        model.addAttribute("user", user);
    }

    @GetMapping("/js/{id}")
    public String getProfileJS(@PathVariable String id, Model model) {
        var user = userService.getUserById(id);
        var statistics = trackedStatisticService.getTrackedStatisticsByUser(user);
        model.addAttribute("user", user);
        model.addAttribute("statistics", statistics);
        return "js/profile.js";
    }

    @GetMapping(value = "/togglemail")
    public String toggleMail() {
        userService.changeEmailNotifications();
        return "redirect:/";
    }
}
