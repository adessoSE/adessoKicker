package de.adesso.kicker.site.controller;

import de.adesso.kicker.ranking.service.RankingService;
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

    private final RankingService rankingService;

    @GetMapping(value = { "/", "/home", "/ranking" })
    public ModelAndView ranking(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        ModelAndView modelAndView = new ModelAndView();
        var users = userService.getUserPageSortedByRating(page, size);
        try {
            var user = userService.getLoggedInUser();
            var rank = rankingService.getPositionOfPlayer(user.getRanking());
            modelAndView.addObject("user", user);
            modelAndView.addObject("rank", rank);
        } catch (UserNotFoundException e) {
            modelAndView.addObject("user", false);
            modelAndView.addObject("rank", false);
        }
        modelAndView.addObject("users", users);
        modelAndView.setViewName("sites/ranking.html");
        return modelAndView;
    }
}
