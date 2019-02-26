package de.adesso.kicker.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping(value = {"/", "/home", "/ranking"})
    public ModelAndView ranking() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sites/ranking.html");
        return modelAndView;
    }

    @GetMapping("/profile")
    public ModelAndView profile() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sites/profile.html");
        return modelAndView;
    }

    @GetMapping("/matchresult")
    public ModelAndView matchresult() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("sites/matchresult.html");
        return modelAndView;
    }
}
