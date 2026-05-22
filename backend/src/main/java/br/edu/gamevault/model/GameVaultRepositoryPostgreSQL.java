package br.edu.gamevault.model;

import java.util.List;
import java.util.Optional;

public class GameVaultRepositoryPostgreSQL implements GameVaultRepository{
    @Override
    public User addUser(User user) {
        
    }

    @Override
    public Optional<User> searchUserByID(int userID) {

    }

    @Override
    public Game addGame(Game game) {

    }

    @Override
    public Optional<Game> searchGameByID(int gameID) {

    }
  
    @Override
    public List<Game> listGames() {

    }

    @Override
    public Review addReview(Review review) {

    }

    @Override
    public List<Review> searchReviewByUser(int userID) {

    }

    @Override
    public List<Review> searchReviewByGame(int gameID) {

    }

}
