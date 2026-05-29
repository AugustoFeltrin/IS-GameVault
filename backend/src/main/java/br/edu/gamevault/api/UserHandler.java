package br.edu.gamevault.api;
import br.edu.gamevault.service.UserService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;

public class UserHandler implements HttpHandler {

    private final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        String method = exchange.getRequestMethod();

        String response;

        if (method.equalsIgnoreCase("GET")) {

            response = "Endpoint GET de usuários";

        } else if (method.equalsIgnoreCase("POST")) {

            response = "Endpoint POST de usuários";

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