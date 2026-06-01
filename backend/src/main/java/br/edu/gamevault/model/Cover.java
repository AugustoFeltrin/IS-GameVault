package br.edu.gamevault.model;

public class Cover {
    private String url;

    public String getUrl() {
        return "https:" + url.replace("t_thumb", "t_cover_big");
    }
}