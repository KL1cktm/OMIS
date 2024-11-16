package by.yurhilevich.WebApp.repository;

import by.yurhilevich.WebApp.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.review.id = :reviewId ORDER BY c.date DESC")
    List<Comment> findAllCommentsByReviewIdOrderedByDate(@Param("reviewId") Long reviewId);
}
