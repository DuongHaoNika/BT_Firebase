package com.example.firebaseapp.model;

public class Showtime {
    private String id;
    private String movieId;
    private String theaterId;
    private String room;
    private long startTime;

    public Showtime() {}

    public Showtime(String id, String movieId, String theaterId, String room, long startTime) {
        this.id = id;
        this.movieId = movieId;
        this.theaterId = theaterId;
        this.room = room;
        this.startTime = startTime;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getMovieId() { return movieId; }
    public void setMovieId(String movieId) { this.movieId = movieId; }

    public String getTheaterId() { return theaterId; }
    public void setTheaterId(String theaterId) { this.theaterId = theaterId; }

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }

    public long getStartTime() { return startTime; }
    public void setStartTime(long startTime) { this.startTime = startTime; }
}