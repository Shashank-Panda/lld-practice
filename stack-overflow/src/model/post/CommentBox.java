package model.post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommentBox {
    private final List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public List<Comment> getComments() {
        return Collections.unmodifiableList(comments);
    }
}
