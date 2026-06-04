package br.edu.gamevault.client;

import br.edu.gamevault.model.Game;
import com.google.gson.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
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
        
        String query = "search \"" + gameName + "\"; fields name, summary, cover.url;";

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

        JsonArray jsonArray = JsonParser.parseString(response.body()).getAsJsonArray();
        List<Game> games = new ArrayList<>();

        for (JsonElement element : jsonArray) {
            JsonObject obj = element.getAsJsonObject();
            
            // Extraindo valores conforme a ordem do seu record: 
            // (int id, String title, String description, String coverUrl, String igdbId)
            int id = obj.has("id") ? obj.get("id").getAsInt() : 0;
            String title = obj.has("name") ? obj.get("name").getAsString() : "Sem nome";
            String summary = obj.has("summary") ? obj.get("summary").getAsString() : "Sem sinopse";
            String coverUrl = (obj.has("cover") && obj.get("cover").getAsJsonObject().has("url")) 
                              ? "https:" + obj.get("cover").getAsJsonObject().get("url").getAsString() 
                              : null;
            String igdbId = String.valueOf(id);

            games.add(new Game(id, title, summary, coverUrl, igdbId));
        }
        
        return games;
    }
}