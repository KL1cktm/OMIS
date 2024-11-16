package by.yurhilevich.WebApp.service;

import by.yurhilevich.WebApp.models.Comment;
import by.yurhilevich.WebApp.models.Review;
import by.yurhilevich.WebApp.models.User;
import by.yurhilevich.WebApp.repository.CommentRepository;
import by.yurhilevich.WebApp.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserService userService;

    @Autowired
    CommentRepository commentRepository;

    @Override
    public void addComment(Long reviewId, String text) {
        if (text.length() >= 500) {
            return;
        }
        Review review = reviewRepository.findById(reviewId).get();
        Comment comment = new Comment();
        comment.setReview(review);
        comment.setText(text);
        comment.setUser(userService.getAuthorizedUser());
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> getSortedCommentsByDate(Review review) {
        return commentRepository.findAllCommentsByReviewIdOrderedByDate(review.getId());
    }

    @Override
    public List<Comment> getCommentForReview(){
        List<Comment> commentsOffer = new ArrayList<>();
        Long userId = userService.getAuthorizedUser().getId();
        List<Comment> comments = commentRepository.findAll();
        for (Comment comment : comments) {
            if (comment.getUser().getRole().equals(User.Role.ROLE_ADMIN)) {
                commentsOffer.add(comment);
            }
        }
        return commentsOffer;
    }
}
