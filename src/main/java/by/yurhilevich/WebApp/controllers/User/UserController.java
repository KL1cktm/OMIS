package by.yurhilevich.WebApp.controllers.User;

import by.yurhilevich.WebApp.models.User;
import by.yurhilevich.WebApp.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Transactional
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("signup")
    public String processSignup(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userService.isUserExists(user.getEmail(),user.getPhone(),user.getUsername())){
            return "redirect:/signup1?error=true";
        }
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loadAuthorizationPage(Model model){
        return "login";
    }

    @PostMapping("/login_processing")
    public String processLogin(String email,String password){
        userService.loadUserByUsername(email);
        return "redirect:/";
    }

    @GetMapping("/signup1")
    public String loadAuthorizationPage1(Model model){
        return "signup1";
    }
}
