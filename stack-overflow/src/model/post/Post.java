package model.post;

import java.time.Instant;
import model.User;


public abstract class Post {
    private String id;
    private String content;
    private User author;
    private Instant createdAt;
    // private Instant updatedAt;

    protected Post(String content, User author) {
        this.id = java.util.UUID.randomUUID().toString();
        this.content = content;
        this.author = author;
        this.createdAt = Instant.now();
    }
}
