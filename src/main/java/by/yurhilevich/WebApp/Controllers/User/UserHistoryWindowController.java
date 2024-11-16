package by.yurhilevich.WebApp.Controllers.User;

import by.yurhilevich.WebApp.service.CommentService;
import by.yurhilevich.WebApp.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller()
@RequestMapping("/user")
public class UserHistoryWindowController {
    @Autowired
    ReviewService reviewService;

    @Autowired
    CommentService commentService;

    @GetMapping("/history")
    public String userHistoryWindow(Model model) {
        System.out.println(reviewService.getUserHistory().size());
        model.addAttribute("reviews", reviewService.getUserHistory());
        model.addAttribute("admin_comments", commentService.getCommentForReview());
        return "user/history";
    }

    @PostMapping("/deleteReview")
    public String deleteReview(@RequestParam("reviewIdForDelete") Long reviewId, Model model) {
        reviewService.deleteReview(reviewId);
        return "redirect:/user/history";
    }
}
