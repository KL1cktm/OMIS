package by.yurhilevich.WebApp.Controllers.User;

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
@RequestMapping("/user")
public class UserReviewController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    ItemService itemService;

    @GetMapping("/create_review")
    public String createReview(Model model) {
        addAttributes(model);
        if (model.containsAttribute("items")) {
            model.addAttribute("items", model.getAttribute("items"));
        } else {
            model.addAttribute("items", itemService.getAllItems());
        }
        return "user/create_review_main";
    }

    @PostMapping("/chooseCategoryInReviewMain")
    public String chooseCategory(@RequestParam("categoryName") String category, RedirectAttributes redirectAttributes) {
        System.out.println("chooseCategory");
        redirectAttributes.addFlashAttribute("items", categoryService.loadItemsByCategory(category));
        return "redirect:/user/create_review";
    }

    public void addAttributes(Model model) {
        model.addAttribute("categories", categoryService.getAllCategoriesInString());
    }
}
