package by.yurhilevich.WebApp.service;

import by.yurhilevich.WebApp.models.Comment;
import by.yurhilevich.WebApp.models.Review;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentService {
    void addComment(Long reviewId, String text);
    List<Comment> getSortedCommentsByDate(Review review);
    List<Comment> getCommentForReview();
}

