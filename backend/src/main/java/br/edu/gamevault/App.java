package br.edu.gamevault;

import br.edu.gamevault.api.GameHandler;
import br.edu.gamevault.api.ReviewHandler;
import br.edu.gamevault.api.UserHandler;

import br.edu.gamevault.model.GameVaultRepository;
import br.edu.gamevault.model.GameVaultRepositoryPostgres;

import br.edu.gamevault.service.GameService;
import br.edu.gamevault.service.GameServiceImpl;

import br.edu.gamevault.service.ReviewService;
import br.edu.gamevault.service.ReviewServiceImpl;

import br.edu.gamevault.service.UserService;
import br.edu.gamevault.service.UserServiceImpl;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

public class App {

    public static void main(String[] args) throws Exception {

        GameVaultRepository repository = new GameVaultRepositoryPostgres();

        UserService userService = new UserServiceImpl(repository);
        GameService gameService = new GameServiceImpl(repository);
        ReviewService reviewService = new ReviewServiceImpl(repository);

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/users", new UserHandler(userService));
        server.createContext("/games", new GameHandler(gameService));
        server.createContext("/reviews", new ReviewHandler(reviewService));

        server.setExecutor(null);

        server.start();

        System.out.println("Servidor rodando em http://localhost:8080");
    }
}