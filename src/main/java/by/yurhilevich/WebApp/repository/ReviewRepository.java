package by.yurhilevich.WebApp.repository;

import by.yurhilevich.WebApp.models.Comment;
import by.yurhilevich.WebApp.models.Item;
import by.yurhilevich.WebApp.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.item.id = :itemId AND r.status = 'SUCCESS'")
    Double findAverageRatingByItemId(@Param("itemId") Long itemId);

    List<Review> findAllByUserId(Long userId);
    boolean existsByUserIdAndItemId(Long userId, Long itemId);

    List<Review> findAllByItem(Item item);
}

