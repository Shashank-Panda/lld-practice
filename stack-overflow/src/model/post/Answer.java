package model.post;

import java.util.List;

import enums.VoteType;
import model.User;

public class Answer extends Post implements Votable {
    private Question question;
    private boolean isAccepted;
    private final VoteBox voteBox = new VoteBox(this);
    private final CommentBox commentBox = new CommentBox();

    public Answer(String content, User author, Question question) {
        super(content, author);
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    void markAccepted() {
        this.isAccepted = true;
    }

    @Override
    public boolean vote(User user, VoteType voteType) {
        return voteBox.vote(user, voteType);
    }

    @Override
    public int getVoteCount() {
        return voteBox.getVoteCount();
    }

    public void addComment(Comment comment) {
        commentBox.addComment(comment);
    }

    public List<Comment> getComments() {
        return commentBox.getComments();
    }
}
