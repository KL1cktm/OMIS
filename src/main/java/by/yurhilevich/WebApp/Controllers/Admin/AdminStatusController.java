package by.yurhilevich.WebApp.Controllers.Admin;

import by.yurhilevich.WebApp.models.Review;
import by.yurhilevich.WebApp.service.CommentService;
import by.yurhilevich.WebApp.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminStatusController {
    @Autowired
    ReviewService reviewService;

    @Autowired
    CommentService commentService;

    @GetMapping("/admin_status")
    public String admin_status(Model model) {
        model.addAttribute("reviews",reviewService.getAllReviews());
        return "admin/admin_status";
    }

    @PostMapping("/submitReview")
    public String submitReview(@RequestParam("reviewId") Long reviewId,
                               @RequestParam("comment") String text, Model model) {
        commentService.addComment(reviewId,text);
        return "redirect:/admin/admin_status";
    }

    @PostMapping("/updateStatus")
    public String updateStatus(@RequestParam("reviewId") Long id,
                               @RequestParam("status") String status, Model model) {
        reviewService.updateStatus(status,id);
        return "redirect:/admin/admin_status";
    }
}
