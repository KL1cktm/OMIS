package by.yurhilevich.WebApp.Controllers.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminManageFeedBackController {
    @GetMapping("/admin_manage_feedback")
    public String admin_manage_feedback(Model model) {
        return "admin/admin_manage_feedback";
    }
}
