package model.post;

import java.time.Instant;
import model.User;


public abstract class Post {
    private String id;
    private String content;
    private User author;
    private Instant createdAt;

    protected Post(String content, User author) {
        this.id = java.util.UUID.randomUUID().toString();
        this.content = content;
        this.author = author;
        this.createdAt = Instant.now();
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public User getAuthor() {
        return author;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

}
