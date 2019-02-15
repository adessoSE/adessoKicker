package de.adesso.kicker.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SiteController {

    @GetMapping("/home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("user/home");
        return modelAndView;
    }

    @GetMapping("/impressum")
    public ModelAndView impressum() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/impressum");
        return modelAndView;
    }
}
