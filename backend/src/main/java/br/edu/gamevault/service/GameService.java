package br.edu.gamevault.service;
import br.edu.gamevault.model.Game;
import java.util.List;
import java.util.Optional;

public interface GameService {
    Game addGame(Game game);
    List<Game> getAllGames();
    Optional<Game> getGameById(int id);
}