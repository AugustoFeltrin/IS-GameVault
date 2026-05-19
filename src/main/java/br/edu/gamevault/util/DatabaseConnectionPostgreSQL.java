package br.edu.gamevault.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionPostgreSQL {
    private static final String URL = "jdbc:postgresql://localhost:5432/gamevault";
    private static final String USER = "Bota o banco";
    private static final String PASSWORD = "Bota a senha";

    /**
     * @return 
     * @throws SQLException 
     */
    
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Erro: O Driver do PostgreSQL não foi encontrado. Verifique o pom.xml.", e);
        }
    }
}