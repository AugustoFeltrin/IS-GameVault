package br.edu.gamevault.api;

import br.edu.gamevault.model.User;
import br.edu.gamevault.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class UserHandler implements HttpHandler {

    private final UserService userService;
    private final ObjectMapper mapper = new ObjectMapper(); // O tradutor JSON!

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // 1. CONFIGURAÇÃO DO CORS (Obrigatório para o React não chorar)
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "http://localhost:5173");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");

        String method = exchange.getRequestMethod();

        // 2. O NAVEGADOR TESTANDO A CONEXÃO (Preflight)
        if (method.equalsIgnoreCase("OPTIONS")) {
            exchange.sendResponseHeaders(204, -1);
            return;
        }

        try {
            String path = exchange.getRequestURI().getPath();

            // 3. ROTEAMENTO E PROCESSAMENTO
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
        // Lê o JSON que veio do React
        InputStream is = exchange.getRequestBody();
        User incomingUser = mapper.readValue(is, User.class);

        // Chama o Service
        User createdUser = userService.addUser(incomingUser);

        // Devolve o JSON de sucesso
        String jsonResponse = mapper.writeValueAsString(createdUser);
        sendResponse(exchange, 201, jsonResponse);
    }

    private void handleLogin(HttpExchange exchange) throws IOException {
        // Implementar leitura do email/senha e chamada do userService.login()...
        // (Semelhante ao Register, mas validando credenciais)
    }

    // Método utilitário para não repetir código de resposta
    private void sendResponse(HttpExchange exchange, int statusCode, String jsonResponse) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        byte[] responseBytes = jsonResponse.getBytes("UTF-8");
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        os.close();
    }
}