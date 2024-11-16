package by.yurhilevich.WebApp.controllers;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class ActivityController {

    @GetMapping("/choose_activity")
    public String chooseActivity(Model model) {
        return "admin/choose_activity";
    }
}
