package br.edu.gamevault.util;


import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionPostgreSQL {

    private static final Dotenv dotenv = Dotenv.configure()
            .directory("./backend") 
            .ignoreIfMissing()
            .load();

    private static final String URL = dotenv.get("DB_URL");
    private static final String USER = dotenv.get("DB_USER");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");
 
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Erro: O Driver do PostgreSQL não foi encontrado. Verifique o pom.xml.", e);
        }
    }

    public static void main(String[] args) {
        System.out.println("Tentando conectar ao banco do GameVault...");
        
        try (Connection conn = DatabaseConnectionPostgreSQL.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ SUCESSO! O link, a porta e as credenciais estão 100% corretos.");
            }
        } catch (SQLException e) {
            System.err.println("❌ ERRO AO CONECTAR!");
            System.err.println("Mensagem do banco: " + e.getMessage());
            System.err.println("\nVerifique se:");
            System.err.println("1. O pgAdmin/Postgres está realmente iniciado.");
            System.err.println("2. A porta (5432) é a mesma do pgAdmin.");
            System.err.println("3. O usuário '" + USER + "' e a senha existem.");
        }
    }

}   