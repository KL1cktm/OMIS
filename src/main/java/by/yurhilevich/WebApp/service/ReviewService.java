package by.yurhilevich.WebApp.service;

import by.yurhilevich.WebApp.models.Item;
import by.yurhilevich.WebApp.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewService {
    double getAvgRating(Long itemId);
    List<Review> getUserHistory();
    boolean addReview(Long itemId, String text, Double rating);
    void deleteReview(Long reviewId);
    Review getReview(Long reviewId);
    List<Review> getReviewsByCategory(String categoryName);
    List<Review> getAllReviews();
    void updateStatus(String status, Long reviewId);
}
