package br.edu.gamevault.model;

import java.time.LocalDate;

public record Review(int id, int userId, int gameId, Integer rating, String comment, LocalDate reviewDate) {

}
