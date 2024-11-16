package by.yurhilevich.WebApp.controllers;

import by.yurhilevich.WebApp.models.User;
import by.yurhilevich.WebApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin_panel")
    public String createAdminPanel( Model model) {
        List<User> users = userService.getAllUsers();
        List<String> roles = Arrays.asList( "ROLE_ANALYST", "ROLE_ADMIN", "ROLE_DIRECTOR" );
        model.addAttribute("users", users);
        model.addAttribute( "roles", roles );
        return "admin/admin_panel";
    }

    @PostMapping("/changeRole")
    public String changeUserRole( @RequestParam Long userId, @RequestParam String role,@RequestParam String userLogin, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String login = authentication.getName();
            System.out.println(login);
            if (login.equals( userLogin )) {
                System.out.println("Error");
                return "redirect:/admin/admin_panel?error_login=true";
            }
        }
        User.Role newRole = User.Role.valueOf( role );
        userService.changeUserRole( userId,newRole );
        return "redirect:/admin/admin_panel";
    }

}
