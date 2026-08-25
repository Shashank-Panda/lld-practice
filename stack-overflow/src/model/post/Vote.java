package model.post;

import enums.VoteType;
import model.User;

public class Vote {
    private final User voter;
    private final Post post;
    private final VoteType voteType;

    public Vote(User voter, Post post, VoteType voteType) {
        this.voter = voter;
        this.post = post;
        this.voteType = voteType;
    }

    public User getVoter() {
        return voter;
    }

    public Post getPost() {
        return post;
    }

    public VoteType getVoteType() {
        return voteType;
    }
}
