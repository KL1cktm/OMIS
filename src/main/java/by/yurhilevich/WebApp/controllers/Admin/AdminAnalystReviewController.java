package by.yurhilevich.WebApp.controllers.Admin;

import by.yurhilevich.WebApp.service.CategoryService;
import by.yurhilevich.WebApp.service.ReviewService;
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
public class AdminAnalystReviewController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    ReviewService reviewService;

    @GetMapping("/analyst_topics")
    public String analystReviews(Model model) {
        model.addAttribute("categories",categoryService.getAllCategoriesInString());
        if (model.containsAttribute("reviews")) {
            model.addAttribute("reviews", model.getAttribute("reviews"));
        } else {
            model.addAttribute("reviews", reviewService.getReviewsByCategory("Все"));
        }
        return "admin/analyst_reviews";
    }

    @PostMapping("/chooseCategoryForAnalystReview")
    public String chooseCategoryForAnalyst(@RequestParam("categoryName") String category, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("reviews", reviewService.getReviewsByCategory(category));
        return "redirect:/admin/analyst_topics";
    }
}
