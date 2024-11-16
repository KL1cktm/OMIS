package by.yurhilevich.WebApp.Controllers.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserFeedbackMainWindowController {
    @GetMapping("/user_info")
    public String user_info(Model model) {
        return "user/user_info";
    }
}
