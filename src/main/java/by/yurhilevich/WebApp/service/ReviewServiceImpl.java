package by.yurhilevich.WebApp.service;

import by.yurhilevich.WebApp.models.Category;
import by.yurhilevich.WebApp.models.Item;
import by.yurhilevich.WebApp.models.Review;
import by.yurhilevich.WebApp.repository.ItemRepository;
import by.yurhilevich.WebApp.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserService userService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    CategoryService categoryService;

    @Override
    public double getAvgRating(Long itemId) {
        Double averageRating = reviewRepository.findAverageRatingByItemId(itemId);
        if (averageRating == null) {
            averageRating = 0.0;
        }
        return averageRating;
    }

    @Override
    public List<Review> getUserHistory(){
        Long id = userService.getAuthorizedUser().getId();
        return reviewRepository.findAllByUserId(id);
    }

    @Override
    public boolean addReview(Long itemId, String text, Double rating) {
        Long userId = userService.getAuthorizedUser().getId();
        if (reviewRepository.existsByUserIdAndItemId(userId, itemId)) {
            return false;
        }
        Review review = new Review();
        review.setItem(itemRepository.findById(itemId).get());
        review.setDefinition(text);
        review.setRating(rating);
        review.setUser(userService.getAuthorizedUser());
        reviewRepository.save(review);
        return true;
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewRepository.delete(reviewRepository.findById(reviewId).get());
    }

    @Override
    public Review getReview(Long reviewId) {
        return reviewRepository.findById(reviewId).get();
    }

    @Override
    public List<Review> getReviewsByCategory(String categoryName) {
        List<Review> reviews = new ArrayList<>();
        if (categoryName.isEmpty() || categoryName.equals("Все")) {
            reviews = reviewRepository.findAll();
            for (Review review : reviews) {
                review.getUser().getUsername(); // Инициализируем user
            }
        } else {
            Category category = categoryService.getCategoryByName(categoryName);
            List<Item> items = itemRepository.findAllByCategories(category);
            for (Item item : items) {
                List<Review> itemReviews = reviewRepository.findAllByItem(item);
                // Явная инициализация user для каждого отзыва
                for (Review review : itemReviews) {
                    review.getUser().getUsername(); // Инициализируем user
                }
                reviews.addAll(itemReviews);
            }
        }
        return reviews;
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public void updateStatus(String status, Long reviewId) {
        Review review = reviewRepository.findById(reviewId).get();
        review.setStatus(Review.Status.valueOf(status));
        reviewRepository.save(review);
    }
}
