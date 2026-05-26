package br.edu.gamevault.service;
import br.edu.gamevault.model.*;
import java.util.List;
import java.util.Optional;

public class GameServiceImpl implements GameService {
    private final GameVaultRepository repository;
    public GameServiceImpl(GameVaultRepository repository) { this.repository = repository; }

    @Override
    public Game addGame(Game game) { return repository.addGame(game); }
    
    @Override
    public List<Game> getAllGames() { return repository.listGames(); }
    
    @Override
    public Optional<Game> getGameById(int id) { return repository.searchGameByID(id); }
}