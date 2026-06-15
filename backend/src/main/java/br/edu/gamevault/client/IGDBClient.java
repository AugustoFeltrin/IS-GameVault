package br.edu.gamevault.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import br.edu.gamevault.dto.GameDTO;
import br.edu.gamevault.model.Game;

public class IGDBClient {
    private final String clientId;
    private final String accessToken;
    private static final String API_URL = "https://api.igdb.com/v4/games";

    public IGDBClient(String clientId, String accessToken) {
        this.clientId = clientId;
        this.accessToken = accessToken;
    }

    public List<Game> searchGame(String gameName) throws Exception {
        String query = "search \"" + gameName + "\"; fields id, name, summary, cover.url; limit 10;";
        
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
            String coverUrl = (dto.cover != null && dto.cover.url != null) 
                ? "https:" + dto.cover.url.replace("t_thumb", "t_1080p") 
                : "https://images.igdb.com/igdb/image/upload/t_1080p/nocover.png";
            
            return new Game(0, dto.name, dto.summary, coverUrl, String.valueOf(dto.id));
        }).collect(Collectors.toList());
    }
}