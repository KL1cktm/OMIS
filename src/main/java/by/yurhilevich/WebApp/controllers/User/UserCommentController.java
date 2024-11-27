package by.yurhilevich.WebApp.controllers.User;

import by.yurhilevich.WebApp.models.Review;
import by.yurhilevich.WebApp.service.CommentService;
import by.yurhilevich.WebApp.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserCommentController {

    @Autowired
    ReviewService reviewService;

    @Autowired
    CommentService commentService;

    @PostMapping("/postComment")
    public String postComment(@RequestParam("reviewForComment")Review review, Model model) {
        model.addAttribute("averageRating", reviewService.getAvgRating(review.getItem().getId()));
        model.addAttribute("review", review);
        model.addAttribute("comments", commentService.getSortedCommentsByDate(review));
        return "user/user_comment";
    }

    @PostMapping("/writeComment")
    public String writeComment(@RequestParam("reviewForComment")Long reviewId,
                               @RequestParam("commentText") String text, Model model) {
        commentService.addComment(reviewId, text);
//        model.addAttribute("averageRating", reviewService.getAvgRating(reviewId));
//        model.addAttribute("review", reviewService.getReview(reviewId));
        return "redirect:/user/user_rating";
    }
}
