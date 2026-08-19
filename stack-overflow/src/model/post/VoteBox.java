package model.post;

import java.util.HashMap;
import java.util.Map;

import enums.Vote;
import model.User;

public class VoteBox {
    private final Map<User, Vote> votes = new HashMap<>();

    public void vote(User user, Vote voteType) {
        votes.put(user, voteType);
    }

    public int getVoteCount() {
        int count = 0;
        for (Vote v : votes.values()) {
            count += (v == Vote.UPVOTE) ? 1 : -1;
        }
        return count;
    }
}
