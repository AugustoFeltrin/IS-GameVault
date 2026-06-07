package br.edu.gamevault.api;

import br.edu.gamevault.model.Review;
import br.edu.gamevault.service.ReviewService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class ReviewHandler implements HttpHandler {

    private final ReviewService reviewService;
    private final ObjectMapper mapper = new ObjectMapper();

    public ReviewHandler(ReviewService reviewService) {
        this.reviewService = reviewService;
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

            if (method.equalsIgnoreCase("POST") && path.endsWith("/register")) {

                handleRegisterReview(exchange);

            } else if (method.equalsIgnoreCase("GET") && path.contains("/game/")) {

                handleReviewsByGame(exchange);

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

    private void handleRegisterReview(HttpExchange exchange) throws IOException {

        InputStream is = exchange.getRequestBody();

        Review incomingReview = mapper.readValue(is, Review.class);

        Review createdReview = reviewService.addReview(incomingReview);

        String jsonResponse = mapper.writeValueAsString(createdReview);

        sendResponse(exchange, 201, jsonResponse);
    }

    private void handleReviewsByGame(HttpExchange exchange) throws IOException {

        String path = exchange.getRequestURI().getPath();

        String[] parts = path.split("/");

        int gameId = Integer.parseInt(parts[parts.length - 1]);

        List<Review> reviews =
                reviewService.getReviewsByGameId(gameId);

        String jsonResponse = mapper.writeValueAsString(reviews);

        sendResponse(exchange, 200, jsonResponse);
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