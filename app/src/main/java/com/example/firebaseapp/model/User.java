package com.example.firebaseapp.model;

public class User {
    private String uid;
    private String email;
    private String name;
    private String fcmToken;

    public User() {}

    public User(String uid, String email, String name, String fcmToken) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.fcmToken = fcmToken;
    }

    public String getUid() { return uid; }
    public void setUid(String uid) { this.uid = uid; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFcmToken() { return fcmToken; }
    public void setFcmToken(String fcmToken) { this.fcmToken = fcmToken; }
}