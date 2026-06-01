package br.edu.gamevault.util;

import io.github.cdimascio.dotenv.Dotenv;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class IGDBAuthService {
    private static final String AUTH_URL = "https://id.twitch.tv/oauth2/token";
    
    private final Dotenv dotenv = Dotenv.configure().directory("./backend").load();
    private final String clientId = dotenv.get("IGDB_CLIENT_ID");
    private final String clientSecret = dotenv.get("IGDB_CLIENT_SECRET");

    public String getAccessToken() throws Exception {
        if (clientId == null || clientSecret == null) {
            throw new RuntimeException("Credenciais não encontradas no arquivo .env!");
        }

        HttpClient client = HttpClient.newHttpClient();

        String body = String.format("client_id=%s&client_secret=%s&grant_type=client_credentials", 
                                    clientId, clientSecret);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(AUTH_URL))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() != 200) {
            throw new RuntimeException("Falha na autenticação. Status: " + response.statusCode() + " Resposta: " + response.body());
        }

        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
        return json.get("access_token").getAsString();
    }
}