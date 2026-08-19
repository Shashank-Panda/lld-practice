package service.reputation;

import enums.Vote;
import model.User;
import model.post.Answer;
import model.post.Post;
import model.post.Question;

public class ReputationManager {
    private final ScoringStrategy scoringStrategy;

    public ReputationManager(ScoringStrategy scoringStrategy) {
        this.scoringStrategy = scoringStrategy;
    }

    public void onQuestionPosted(User author) {
        applyDelta(author, ReputationEventType.QUESTION_POSTED);
    }

    public void onAnswerPosted(User author) {
        applyDelta(author, ReputationEventType.ANSWER_POSTED);
    }

    public void onCommentPosted(User author) {
        applyDelta(author, ReputationEventType.COMMENT_POSTED);
    }

    // target must be a Votable post (Question or Answer) - caller is responsible for that
    public void onVoteCast(Post target, User voter, Vote voteType) {
        User postOwner = target.getAuthor();

        if (voteType == Vote.UPVOTE) {
            if (target instanceof Question) {
                applyDelta(postOwner, ReputationEventType.QUESTION_UPVOTED);
            } else if (target instanceof Answer) {
                applyDelta(postOwner, ReputationEventType.ANSWER_UPVOTED);
            }
        } else {
            applyDelta(postOwner, ReputationEventType.POST_DOWNVOTED);
            applyDelta(voter, ReputationEventType.DOWNVOTE_CAST_PENALTY);
        }
    }

    public void onAnswerAccepted(Answer answer, User asker) {
        applyDelta(answer.getAuthor(), ReputationEventType.ANSWER_ACCEPTED);
        applyDelta(asker, ReputationEventType.ANSWER_ACCEPTED_ASKER_BONUS);
    }

    private void applyDelta(User user, ReputationEventType eventType) {
        int points = scoringStrategy.getPoints(eventType);
        user.addReputation(points);
    }
}
