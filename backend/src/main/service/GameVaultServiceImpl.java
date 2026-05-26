package br.edu.gamevault.service;

import br.edu.gamevault.model.*;
import java.util.List;
import java.util.Optional;

public class GameVaultServiceImpl implements GameVaultService{
    private final GameVaultRepository repository;

    public GameVaultServiceImpl(GameVaultRepository repository){
        this.repository = repository;
    }

    @Override
    public User addUser(User user){
        return repository.addUser(user);
    }

    @Overide
    public Optional<User> getUserById(int id){
        return repository.searchGameByID(id);
    }

    @Override
    public Game addGame(Game game){
        return repository.addGame(game)
    }

    @Override
    public List<Game> getAllGames(){
        return repository.listGames();
    }

    @Override 
    public Optional<Game> getGameById(int id){
        return repository.searchGameByID(id);
    }

    @Public Review addReview(Review review){
        if(review.rating() != null && (review.rating() < 0 || review.rating() > 10)){
            throw new IllegalArgumentException("Rating must be between 0 and 10");
        }
        return repository.addReview(review);
    }

    @Override
    public List<Review> getReviewsByUserId(int userId){
        return repository.searchReviewByUser(userId);
    }

    @Override
    public List<Review> getReviewsByGameId(){
        return repository.searchReviewByGame(gameId);
    }
}
