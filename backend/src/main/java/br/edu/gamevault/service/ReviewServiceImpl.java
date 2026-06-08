package br.edu.gamevault.service;
import java.time.LocalDate;
import java.util.List;

import br.edu.gamevault.model.GameVaultRepository;
import br.edu.gamevault.model.Review;

public class ReviewServiceImpl implements ReviewService {
    private final GameVaultRepository repository;
    public ReviewServiceImpl(GameVaultRepository repository) { this.repository = repository; }

    @Override
    public Review addReview(Review review) {
        if(review.rating() != null && (review.rating() < 0 || review.rating() > 10)){
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }
        
        LocalDate dataAvaliacao = review.reviewDate() != null ? review.reviewDate() : LocalDate.now();
        
        Review reviewParaSalvar = new Review(
            review.id(), review.userId(), review.gameId(), 
            review.rating(), review.comment(), dataAvaliacao
        );

        return repository.addReview(reviewParaSalvar);
    }
    
    @Override
    public List<Review> getReviewsByUserId(int userId) { return repository.searchReviewByUser(userId); }
    
    @Override
    public List<Review> getReviewsByGameId(int gameId) { return repository.searchReviewByGame(gameId); }
}