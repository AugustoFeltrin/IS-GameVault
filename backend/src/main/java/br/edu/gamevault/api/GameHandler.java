package br.edu.gamevault.api;

import br.edu.gamevault.service.GameService;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class GameHandler implements HttpHandler {

    private final GameService gameService;

    public GameHandler(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String method = exchange.getRequestMethod();

        String response;

        if (method.equalsIgnoreCase("GET")) {

            response = "Endpoint GET de jogos";

        } else if (method.equalsIgnoreCase("POST")) {

            response = "Endpoint POST de jogos";

        } else {

            response = "Método não permitido";

            exchange.sendResponseHeaders(405, response.length());

            return;
        }

        exchange.sendResponseHeaders(200, response.length());

        OutputStream os = exchange.getResponseBody();

        os.write(response.getBytes());

        os.close();
    }
}