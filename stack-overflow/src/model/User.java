package model;

import java.util.UUID;

public class User {
    private final String userId;
    private final String username;
    private final String email;
    private final String password;
    private final String userImage;
    private final String shortSummary;
    private double reputation_score;
    //list of posts (categorized by post type)
    //list of votes (vote type mapped to post id)

    public User(String username, String email, String password, String userImage, String shortSummary) {
        this.userId = UUID.randomUUID().toString();
        this.username = username;
        this.email = email;
        this.password = password;
        this.userImage = userImage;
        this.shortSummary = shortSummary;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUserImage() {
        return userImage;
    }

    public String getShortSummary() {
        return shortSummary;
    }

    public double getReputationScore() {
        return reputation_score;
    }

    public synchronized void addReputation(double delta) {
        this.reputation_score += delta;
    }
}
