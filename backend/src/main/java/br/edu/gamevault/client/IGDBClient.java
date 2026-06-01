package br.edu.gamevault.client;

import br.edu.gamevault.model.Game;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class IGDBClient {
    private final String clientId;
    private final String accessToken;
    private static final String API_URL = "https://api.igdb.com/v4/games";

    public IGDBClient(String clientId, String accessToken) {
        this.clientId = clientId;
        this.accessToken = accessToken;
    }

    public List<Game> searchGame(String gameName) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        
        // Define os campos que queremos buscar
        String query = "search \"" + gameName + "\"; fields name, cover.url;";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Client-ID", clientId)
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "text/plain")
                .POST(HttpRequest.BodyPublishers.ofString(query))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        if (response.statusCode() != 200) {
            throw new RuntimeException("Erro na busca: " + response.statusCode() + " - " + response.body());
        }

        Gson gson = new Gson();
        return gson.fromJson(response.body(), new TypeToken<List<Game>>(){}.getType());
    }
}