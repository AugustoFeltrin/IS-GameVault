package br.edu.gamevault;

import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import br.edu.gamevault.api.GameHandler;
import br.edu.gamevault.api.IGDBSearchHandler; 
import br.edu.gamevault.api.ReviewHandler;
import br.edu.gamevault.api.UserHandler;
import br.edu.gamevault.client.IGDBClient;
import br.edu.gamevault.model.GameVaultRepository;
import br.edu.gamevault.model.GameVaultRepositoryPostgres;
import br.edu.gamevault.service.GameService;
import br.edu.gamevault.service.GameServiceImpl;
import br.edu.gamevault.service.ReviewService;
import br.edu.gamevault.service.ReviewServiceImpl;
import br.edu.gamevault.service.UserService;
import br.edu.gamevault.service.UserServiceImpl; 
import br.edu.gamevault.util.IGDBAuthService;
import io.github.cdimascio.dotenv.Dotenv;

public class App {

    public static void main(String[] args) throws Exception {

        GameVaultRepository repository = new GameVaultRepositoryPostgres();
        UserService userService = new UserServiceImpl(repository);
        GameService gameService = new GameServiceImpl(repository);
        ReviewService reviewService = new ReviewServiceImpl(repository);

        System.out.println("Autenticando na IGDB...");
        Dotenv dotenv = Dotenv.configure().load();
        String clientId = dotenv.get("IGDB_CLIENT_ID");

        IGDBAuthService auth = new IGDBAuthService();
        String token = auth.getAccessToken();
        System.out.println("Token da IGDB gerado com sucesso!");

        IGDBClient igdbClient = new IGDBClient(clientId, token);

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/users", new UserHandler(userService));
        server.createContext("/games", new GameHandler(gameService));
        server.createContext("/reviews", new ReviewHandler(reviewService));
        server.createContext("/igdb/search", new IGDBSearchHandler(igdbClient));

        server.setExecutor(null);
        server.start();

        System.out.println("Servidor rodando em http://localhost:8080");
    }
}