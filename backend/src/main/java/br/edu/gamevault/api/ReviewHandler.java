package br.edu.gamevault.api;

import br.edu.gamevault.service.ReviewService;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class ReviewHandler implements HttpHandler {

    private final ReviewService reviewService;

    public ReviewHandler(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String method = exchange.getRequestMethod();

        String response;

        if (method.equalsIgnoreCase("GET")) {

            response = "Endpoint GET de reviews";

        } else if (method.equalsIgnoreCase("POST")) {

            response = "Endpoint POST de reviews";

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