package br.edu.gamevault.model;
import com.google.gson.annotations.SerializedName;

public record Game(@SerializedName("id") int id,  @SerializedName("name") String title, String description, String coverUrl,  String igdbId) {}
