package model.post;

import model.User;

public class Comment extends Post {
    public Comment(String content, User author) {
        super(content, author);
    }
}
