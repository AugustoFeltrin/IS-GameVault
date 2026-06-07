package br.edu.gamevault.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.edu.gamevault.util.DatabaseConnectionPostgreSQL;

public class GameVaultRepositoryPostgres implements GameVaultRepository {
    // User
    @Override
    public User addUser(User user) {
        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnectionPostgreSQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, user.name());
            pstmt.setString(2, user.email());
            pstmt.setString(3, user.password());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return new User(rs.getInt(1), user.name(), user.email(), user.password());
                }
            }
            throw new RuntimeException("Erro ao obter ID gerado para o usuário.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar usuário", e);
        }
    }

    @Override
    public Optional<User> searchUserByID(int userID) {
        String sql = "SELECT id, name, email, password FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnectionPostgreSQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar usuário por ID", e);
        }
        return Optional.empty();
    }

    // Game
    @Override
    public Game addGame(Game game) {
        String sql = "INSERT INTO games (title, description, cover_url, igdb_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnectionPostgreSQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, game.title());
            pstmt.setString(2, game.description());
            pstmt.setString(3, game.coverUrl());
            pstmt.setString(4, game.igdbId());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return new Game(rs.getInt(1), game.title(), game.description(), game.coverUrl(), game.igdbId());
                }
            }
            throw new RuntimeException("Erro ao obter ID gerado para o jogo.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar jogo", e);
        }
    }

    @Override
    public Optional<Game> searchGameByID(int gameID) {
        String sql = "SELECT id, title, description, cover_url, igdb_id FROM games WHERE id = ?";
        try (Connection conn = DatabaseConnectionPostgreSQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, gameID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Game(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("cover_url"),
                        rs.getString("igdb_id")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar jogo por ID", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Game> listGames() {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT id, title, description, cover_url, igdb_id FROM games";
        try (Connection conn = DatabaseConnectionPostgreSQL.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                games.add(new Game(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("cover_url"),
                    rs.getString("igdb_id")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar jogos", e);
        }
        return games;
    }

    // Review
    @Override
    public Review addReview(Review review) {
        String sql = "INSERT INTO reviews (user_id, game_id, rating, comment, review_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnectionPostgreSQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, review.userId());
            pstmt.setInt(2, review.gameId());
            
            pstmt.setObject(3, review.rating(), Types.INTEGER); 
            pstmt.setString(4, review.comment());
            
            pstmt.setDate(5, Date.valueOf(review.reviewDate()));
            
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return new Review(
                        rs.getInt(1), 
                        review.userId(), 
                        review.gameId(), 
                        review.rating(), 
                        review.comment(), 
                        review.reviewDate()
                    );
                }
            }
            throw new RuntimeException("Erro ao obter ID gerado para a avaliação.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar avaliação", e);
        }
    }

    @Override
    public List<Review> searchReviewByUser(int userID) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT id, user_id, game_id, rating, comment, review_date FROM reviews WHERE user_id = ?";
        try (Connection conn = DatabaseConnectionPostgreSQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    reviews.add(mapRowToReview(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar avaliações do usuário", e);
        }
        return reviews;
    }

    @Override
    public List<Review> searchReviewByGame(int gameID) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT id, user_id, game_id, rating, comment, review_date FROM reviews WHERE game_id = ?";
        try (Connection conn = DatabaseConnectionPostgreSQL.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, gameID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    reviews.add(mapRowToReview(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar avaliações do jogo", e);
        }
        return reviews;
    }

    private Review mapRowToReview(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int userId = rs.getInt("user_id");
        int gameId = rs.getInt("game_id");
        
        double rating = rs.getObject("rating", Integer.class); 
        String comment = rs.getString("comment");
        LocalDate reviewDate = rs.getDate("review_date").toLocalDate();
        
        return new Review(id, userId, gameId, rating, comment, reviewDate);
    }
}