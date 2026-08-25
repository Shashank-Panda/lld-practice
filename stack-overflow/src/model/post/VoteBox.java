package model.post;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import enums.VoteType;
import model.User;

public class VoteBox {
    private final Post owner;
    private final Map<User, Vote> votes = new ConcurrentHashMap<>();

    public VoteBox(Post owner) {
        this.owner = owner;
    }

    // returns false if the user already voted on this post - vote is rejected
    public boolean vote(User user, VoteType voteType) {
        Vote vote = new Vote(user, owner, voteType);
        return votes.putIfAbsent(user, vote) == null;
    }

    public Collection<Vote> getVotes() {
        return votes.values();
    }

    public int getVoteCount() {
        int count = 0;
        for (Vote v : votes.values()) {
            count += (v.getVoteType() == VoteType.UPVOTE) ? 1 : -1;
        }
        return count;
    }
}
