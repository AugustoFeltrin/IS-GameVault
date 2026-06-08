package br.edu.gamevault.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import br.edu.gamevault.model.User;
import br.edu.gamevault.service.UserService;

public class UserHandler implements HttpHandler {

    private final UserService userService;
    private final ObjectMapper mapper = new ObjectMapper(); 

    public UserHandler(UserService userService) {
        this.userService = userService;
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

            if (method.equalsIgnoreCase("POST") && path.endsWith("/login")) {
                handleLogin(exchange);
            } 
            else if (method.equalsIgnoreCase("POST") && path.endsWith("/register")) {
                handleRegister(exchange);
            } 
            else {
                sendResponse(exchange, 404, "{\"erro\": \"Rota não encontrada\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, 500, "{\"erro\": \"Erro interno no servidor\"}");
        }
    }

    private void handleRegister(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        User incomingUser = mapper.readValue(is, User.class);

        User createdUser = userService.addUser(incomingUser);

        String jsonResponse = mapper.writeValueAsString(createdUser);
        sendResponse(exchange, 201, jsonResponse);
    }

    private void handleLogin(HttpExchange exchange) throws IOException {
        try {
            InputStream is = exchange.getRequestBody();
            JsonNode jsonNode = mapper.readTree(is);

            if (!jsonNode.has("email") || !jsonNode.has("password")) {
                sendResponse(exchange, 400, "{\"erro\": \"Campos 'email' e 'password' são obrigatórios.\"}");
                return;
            }

            String email = jsonNode.get("email").asText().trim();
            String password = jsonNode.get("password").asText();

            Optional<User> authenticatedUser = userService.authenticate(email, password);

            if (authenticatedUser.isPresent()) {
                String jsonResponse = mapper.writeValueAsString(authenticatedUser.get());
                sendResponse(exchange, 200, jsonResponse);
            } else {
                sendResponse(exchange, 401, "{\"erro\": \"E-mail ou senha incorretos.\"}");
            }

        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, 400, "{\"erro\": \"JSON inválido ou mal formatado.\"}");
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String jsonResponse) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        byte[] responseBytes = jsonResponse.getBytes("UTF-8");
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        os.close();
    }
}