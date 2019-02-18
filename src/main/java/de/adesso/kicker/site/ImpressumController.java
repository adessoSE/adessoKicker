package de.adesso.kicker.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/impressum")
public class ImpressumController {

    @GetMapping
    public ModelAndView impressum() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/impressum.html");
        return modelAndView;
    }
}
