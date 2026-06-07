package br.edu.gamevault.model;

import java.util.List;
import java.util.Optional;

public interface GameVaultRepository {
    // User
    User addUser(User user);
    Optional<User> searchUserByID(int userID);
    Optional<User> findByEmail(String email);
    // Game
    Game addGame(Game game);
    Optional<Game> searchGameByID(int gameID);
    // Resolvemo a encrenca depois
    // Optional<Game> searchGameByIgbdID(int id);
    List<Game> listGames();

    // Review
    Review addReview(Review review);
    List<Review> searchReviewByUser(int userID);
    List<Review> searchReviewByGame(int gameID);

}
