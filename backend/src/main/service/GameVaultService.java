package br.edu.gamevault.service;

import br.edu.gamevault.model.*;
import java.util.List;
import java.util.Optional;

public interface GameVaultService {
    // USER
    User addUser(User user);
    Optional<User> getUserById(int id);

    // GAME
    Game addGame(Game game);
    List<Game> getAllGames();
    Optional<Game> getGameById(int id);

    // REVIEW
    Review addReview(Review review);
    List<Review> getReviewsByUserId(int userId);
    List<Review> getReviewsByGameId(int gameId);
} 
