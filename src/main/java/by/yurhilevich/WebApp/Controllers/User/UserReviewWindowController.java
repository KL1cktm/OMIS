package by.yurhilevich.WebApp.Controllers.User;

import by.yurhilevich.WebApp.models.Review;
import by.yurhilevich.WebApp.service.CategoryService;
import by.yurhilevich.WebApp.service.ItemService;
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
@RequestMapping("/user")
public class UserReviewWindowController {

    @Autowired
    ItemService itemService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/create_review_for_item")
    public String createReviewWindow(@RequestParam("itemId") Long id, Model model) {
        model.addAttribute("item", itemService.getItemById(id));
        model.addAttribute("averageRating", reviewService.getAvgRating(id));
        return "user/user_review_window";
    }

    @PostMapping("/chooseCategoryInReview")
    public String chooseCategory(@RequestParam("categoryName") String category, RedirectAttributes redirectAttributes) {
        System.out.println("chooseCategory");
        redirectAttributes.addFlashAttribute("items", categoryService.loadItemsByCategory(category));
        return "redirect:/user/create_review_for_item";
    }

    @PostMapping("/writeReview")
    public String writeReview(@RequestParam("itemId") Long id,@RequestParam("reviewText") String text,
                              @RequestParam("rating") Double rating, Model model) {
        if (reviewService.addReview(id, text, rating)) {
            return "redirect:/user/create_review?success=true";
        }
        return "redirect:/user/create_review?failed=true";
    }
}
