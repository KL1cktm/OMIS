package by.yurhilevich.WebApp.Controllers.User;

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
public class UserRatingController {
    @Autowired
    ItemService itemService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/user_rating")
    public String createReviewWindow(Model model) {
        if (model.containsAttribute("items")) {
            model.addAttribute("items", model.getAttribute("items"));
        } else {
            model.addAttribute("items", itemService.getAllItems());
        }
        model.addAttribute("categories", categoryService.getAllCategoriesInString() );
        return "user/user_rating_window";
    }

    @PostMapping("/chooseCategory")
    public String chooseCategory(@RequestParam("categoryName") String category, RedirectAttributes redirectAttributes) {
        System.out.println("chooseCategory");
        redirectAttributes.addFlashAttribute("items", categoryService.loadItemsByCategory(category));
        return "redirect:/user/user_rating";
    }

    @PostMapping("/productDetails")
    public String productDetails(@RequestParam("itemId") Long itemId, Model model) {
        model.addAttribute("product", itemService.getItemById(itemId));
        model.addAttribute("averageRating", reviewService.getAvgRating(itemId));
        return "user/product_details";
    }

    @GetMapping("/productDetails")
    public String productDetailsWithoutParams(Model model) {
        Long itemId =itemService.getItem();
        model.addAttribute("product", itemService.getItemById(itemId));
        model.addAttribute("averageRating", reviewService.getAvgRating(itemId));
        return "user/product_details";
    }
}
