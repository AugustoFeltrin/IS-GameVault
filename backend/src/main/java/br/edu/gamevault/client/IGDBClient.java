package br.edu.gamevault.client;

import br.edu.gamevault.dto.GameDTO;
import br.edu.gamevault.model.Game;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.net.URI;
import java.net.http.*;
import java.util.*;
import java.util.stream.Collectors;

public class IGDBClient {
    private final String clientId;
    private final String accessToken;
    private static final String API_URL = "https://api.igdb.com/v4/games";

    public IGDBClient(String clientId, String accessToken) {
        this.clientId = clientId;
        this.accessToken = accessToken;
    }

    public List<Game> searchGame(String gameName) throws Exception {
        String query = "search \"" + gameName + "\"; fields name, summary, cover.url;";
        
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(API_URL))
            .header("Client-ID", clientId)
            .header("Authorization", "Bearer " + accessToken)
            .POST(HttpRequest.BodyPublishers.ofString(query))
            .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        
        Gson gson = new Gson();
        List<GameDTO> dtos = gson.fromJson(response.body(), new TypeToken<List<GameDTO>>(){}.getType());

        return dtos.stream().map(dto -> {
            String coverUrl = (dto.cover != null) ? "https:" + dto.cover.url : null;
            return new Game(0, dto.name, dto.summary, coverUrl, coverUrl);
        }).collect(Collectors.toList());
    }
}