package model.post;

import enums.Vote;
import model.User;

public class Answer extends Post implements Votable {
    private Question question;
    private boolean isAccepted;
    private final VoteBox voteBox = new VoteBox();

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
    public void vote(User user, Vote voteType) {
        voteBox.vote(user, voteType);
    }

    @Override
    public int getVoteCount() {
        return voteBox.getVoteCount();
    }
}
