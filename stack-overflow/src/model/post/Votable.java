package model.post;

import enums.VoteType;
import model.User;

public interface Votable {
    boolean vote(User user, VoteType voteType);
    int getVoteCount();
}
