package br.edu.gamevault.api;

import br.edu.gamevault.model.Game;
import br.edu.gamevault.service.GameService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class GameHandler implements HttpHandler {

    private final GameService gameService;
    private final ObjectMapper mapper = new ObjectMapper();

    public GameHandler(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "http://localhost:5173");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

        String method = exchange.getRequestMethod();

        if (method.equalsIgnoreCase("OPTIONS")) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        try {

            String path = exchange.getRequestURI().getPath();

            if (method.equalsIgnoreCase("GET") && path.endsWith("/all")) {

                handleGetAllGames(exchange);

            } else if (method.equalsIgnoreCase("POST") && path.endsWith("/register")) {

                handleRegisterGame(exchange);

            } else {

                sendResponse(exchange, 404,
                        "{\"erro\": \"Rota não encontrada\"}");
            }

        } catch (Exception e) {

            e.printStackTrace();

            sendResponse(exchange, 500,
                    "{\"erro\": \"Erro interno no servidor\"}");
        }
    }

    private void handleGetAllGames(HttpExchange exchange) throws IOException {

        List<Game> games = gameService.getAllGames();

        String jsonResponse = mapper.writeValueAsString(games);

        sendResponse(exchange, 200, jsonResponse);
    }

    private void handleRegisterGame(HttpExchange exchange) throws IOException {

        InputStream is = exchange.getRequestBody();

        Game incomingGame = mapper.readValue(is, Game.class);

        Game createdGame = gameService.addGame(incomingGame);

        String jsonResponse = mapper.writeValueAsString(createdGame);

        sendResponse(exchange, 201, jsonResponse);
    }

    private void sendResponse(HttpExchange exchange,
                              int statusCode,
                              String jsonResponse) throws IOException {

        exchange.getResponseHeaders()
                .set("Content-Type", "application/json");

        byte[] responseBytes = jsonResponse.getBytes("UTF-8");

        exchange.sendResponseHeaders(statusCode, responseBytes.length);

        OutputStream os = exchange.getResponseBody();

        os.write(responseBytes);

        os.close();
    }
}