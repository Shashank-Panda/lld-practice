package model.post;

import enums.Vote;
import model.User;

public interface Votable {
    void vote(User user, Vote voteType);
    int getVoteCount();
}
