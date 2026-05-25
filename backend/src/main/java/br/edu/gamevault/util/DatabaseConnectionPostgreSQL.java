package br.edu.gamevault.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseConnectionPostgreSQL {

    private static final Dotenv dotenv = Dotenv.configure()
            .directory("./") 
            .ignoreIfMissing()
            .load();

    private static final String URL = dotenv.get("DB_URL");
    private static final String USER = dotenv.get("DB_USER");
    private static final String dbPASSWORD = dotenv.get("GAMEVAULT_DB_PASS");
 
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            
            return DriverManager.getConnection(URL, USER, dbPASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Erro: O Driver do PostgreSQL não foi encontrado. Verifique o pom.xml.", e);
        }
    }
}   