package by.yurhilevich.WebApp.controllers;

import by.yurhilevich.WebApp.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Transactional
public class ProfileController {
    @Autowired
    UserService userService;

    @GetMapping("/profile")
    public String profile(Model model) {
        model.addAttribute("user",userService.getAuthorizedUser());
        return "profile";
    }




}
