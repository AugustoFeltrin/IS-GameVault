package br.edu.gamevault.dto;

public class GameDTO {
    public int id; 
    public String name;
    public String summary;
    public Cover cover;

    public static class Cover {
        public String url;
    }
}