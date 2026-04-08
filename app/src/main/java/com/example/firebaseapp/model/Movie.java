package com.example.firebaseapp.model;

public class Movie {
    private String id;
    private String title;
    private String description;
    private int duration;
    private String posterUrl;

    public Movie() {}

    public Movie(String id, String title, String description, int duration, String posterUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.posterUrl = posterUrl;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public String getPosterUrl() { return posterUrl; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }
}