package br.edu.gamevault;

import br.edu.gamevault.util.IGDBAuthService;
import br.edu.gamevault.client.IGDBClient;
import br.edu.gamevault.model.Game;
import io.github.cdimascio.dotenv.Dotenv;

public class Main {
    public static void main(String[] args) {
        try {
            Dotenv dotenv = Dotenv.configure().directory("./backend").load();
            String clientId = dotenv.get("IGDB_CLIENT_ID");

            IGDBAuthService auth = new IGDBAuthService();
            String token = auth.getAccessToken();
            System.out.println("Token gerado com sucesso!");

            IGDBClient client = new IGDBClient(clientId, token);

            List<Game> jogos = client.searchGame("Hollow Knight");

            System.out.println("----------------------------------------");
            for (Game g : jogos) {
                System.out.println("Encontrado: " + g.title());
            }
            System.out.println("----------------------------------------");

        } catch (Exception e) {
            System.err.println("Erro na execução: " + e.getMessage());
            e.printStackTrace();
        }
    }
}