package br.edu.gamevault.api;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import br.edu.gamevault.client.IGDBClient;
import br.edu.gamevault.model.Game;

public class IGDBSearchHandler implements HttpHandler {

    private final IGDBClient igdbClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public IGDBSearchHandler(IGDBClient igdbClient) {
        this.igdbClient = igdbClient;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "http://localhost:5173");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        try {
            String query = exchange.getRequestURI().getQuery();
            if (query == null || !query.startsWith("q=")) {
                sendResponse(exchange, 400, "{\"erro\": \"Parâmetro 'q' é obrigatório\"}");
                return;
            }

            String gameName = URLDecoder.decode(query.substring(2), StandardCharsets.UTF_8.name());

            List<Game> jogos = igdbClient.searchGame(gameName);

            String jsonResponse = mapper.writeValueAsString(jogos);
            sendResponse(exchange, 200, jsonResponse);

        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, 500, "{\"erro\": \"Erro ao buscar jogos na IGDB\"}");
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String jsonResponse) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        byte[] responseBytes = jsonResponse.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        os.close();
    }
}