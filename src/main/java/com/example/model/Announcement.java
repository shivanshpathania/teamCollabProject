package com.example.model;

public class Announcement {
    private int id;
    private int teamId;
    private String message;
    private String author; // Mentor or Student
    private String timestamp;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getTeamId() { return teamId; }
    public void setTeamId(int teamId) { this.teamId = teamId; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
