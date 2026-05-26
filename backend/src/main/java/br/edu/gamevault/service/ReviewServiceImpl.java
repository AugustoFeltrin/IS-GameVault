package br.edu.gamevault.service;
import br.edu.gamevault.model.*;
import java.util.List;

public class ReviewServiceImpl implements ReviewService {
    private final GameVaultRepository repository;
    public ReviewServiceImpl(GameVaultRepository repository) { this.repository = repository; }

    @Override
    public Review addReview(Review review) {
        if(review.rating() != null && (review.rating() < 0 || review.rating() > 10)){
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }
        return repository.addReview(review);
    }
    
    @Override
    public List<Review> getReviewsByUserId(int userId) { return repository.searchReviewByUser(userId); }
    
    @Override
    public List<Review> getReviewsByGameId(int gameId) { return repository.searchReviewByGame(gameId); }
}