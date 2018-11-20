//package de.adesso.kicker.user;
//
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//@Controller
//public class LoginController {
//
//    private LoginService loginService;
//
//    @Autowired
//    public LoginController(LoginService loginService) {
//
//        this.loginService = loginService;
//    }
//
//    /** Maps GET requests for "/login" to the login template */
//    @GetMapping("/login")
//    public ModelAndView login() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("user/login");
//        return modelAndView;
//    }
//
//    /**
//     * Maps GET request for "/registration" to the registration template and bind an
//     * empty object of the type user to it
//     */
//    @GetMapping("/registration")
//    public ModelAndView registration() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("user", new User());
//        modelAndView.setViewName("user/registration");
//        return modelAndView;
//    }
//
//    /**
//     * Maps POST request for "/registration" checks for validity of the input and if
//     * there's already an entry in the database for the entered eMail. It everything
//     * is valid and the eMail doesn't exist in the database a new User entry will be
//     * created in the User Table and a message that the User has been created will
//     * be displayed
//     */
//    @PostMapping("/registration")
//    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
//        ModelAndView modelAndView = new ModelAndView();
//        if (bindingResult.hasErrors()) {
//            modelAndView.setViewName("user/registration");
//            return modelAndView;
//        }
//        try {
//            loginService.checkUserExists(user);
//        } catch (UserAlreadyExistsException e) {
//            bindingResult.rejectValue("email", "error.user", "There is already a user with this eMail");
//            modelAndView.setViewName("user/registration");
//            return modelAndView;
//        }
//
//        loginService.registerUser(user);
//        modelAndView.addObject("successMessage", "User has been registered successfully");
//        modelAndView.addObject("user", new User());
//        modelAndView.setViewName("user/registration");
//        return modelAndView;
//    }
//}
