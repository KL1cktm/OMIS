package by.yurhilevich.WebApp.Controllers.Admin;

import by.yurhilevich.WebApp.service.CategoryService;
import by.yurhilevich.WebApp.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminAnalystController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    ItemService itemService;

    @GetMapping("admin_analyst")
    public String admin_analyst(Model model) {
        model.addAttribute("categories", categoryService.getAllCategoriesInString());
        if (model.containsAttribute("items")) {
            model.addAttribute("items", model.getAttribute("items"));
        } else {
            model.addAttribute("items", itemService.getAllItems());
        }
        return "admin/admin_analyst";
    }

    @PostMapping("/chooseCategory")
    public String chooseCategory(@RequestParam("categoryName") String category, RedirectAttributes redirectAttributes) {
        System.out.println("chooseCategory");
        redirectAttributes.addFlashAttribute("items", categoryService.loadItemsByCategory(category));
        return "redirect:/admin/admin_analyst";
    }


}
