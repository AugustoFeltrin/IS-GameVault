package br.edu.gamevault.service;
import br.edu.gamevault.model.Review;
import java.util.List;

public interface ReviewService {
    Review addReview(Review review);
    List<Review> getReviewsByUserId(int userId);
    List<Review> getReviewsByGameId(int gameId);
}