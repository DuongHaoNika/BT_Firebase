package com.example.firebaseapp.model;

public class Ticket {
    private String id;
    private String userId;
    private String movieId;
    private String movieTitle;
    private String theaterName;
    private String showtimeId;
    private long showTime;
    private String seatNumber;
    private long createdAt;

    public Ticket() {}

    public Ticket(String id, String userId, String movieId, String movieTitle,
                  String theaterName, String showtimeId, long showTime,
                  String seatNumber, long createdAt) {
        this.id = id;
        this.userId = userId;
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.theaterName = theaterName;
        this.showtimeId = showtimeId;
        this.showTime = showTime;
        this.seatNumber = seatNumber;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getMovieId() { return movieId; }
    public void setMovieId(String movieId) { this.movieId = movieId; }

    public String getMovieTitle() { return movieTitle; }
    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }

    public String getTheaterName() { return theaterName; }
    public void setTheaterName(String theaterName) { this.theaterName = theaterName; }

    public String getShowtimeId() { return showtimeId; }
    public void setShowtimeId(String showtimeId) { this.showtimeId = showtimeId; }

    public long getShowTime() { return showTime; }
    public void setShowTime(long showTime) { this.showTime = showTime; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
}