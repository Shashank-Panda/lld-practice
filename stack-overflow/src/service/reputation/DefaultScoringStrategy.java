package service.reputation;

import java.util.EnumMap;
import java.util.Map;

public class DefaultScoringStrategy implements ScoringStrategy {
    private final Map<ReputationEventType, Integer> pointsTable = new EnumMap<>(ReputationEventType.class);

    public DefaultScoringStrategy() {
        pointsTable.put(ReputationEventType.QUESTION_POSTED, 1);
        pointsTable.put(ReputationEventType.ANSWER_POSTED, 2);
        pointsTable.put(ReputationEventType.COMMENT_POSTED, 0);
        pointsTable.put(ReputationEventType.QUESTION_UPVOTED, 5);
        pointsTable.put(ReputationEventType.ANSWER_UPVOTED, 10);
        pointsTable.put(ReputationEventType.ANSWER_ACCEPTED, 15);
        pointsTable.put(ReputationEventType.ANSWER_ACCEPTED_ASKER_BONUS, 2);
        pointsTable.put(ReputationEventType.POST_DOWNVOTED, -2);
        pointsTable.put(ReputationEventType.DOWNVOTE_CAST_PENALTY, -1);
    }

    @Override
    public int getPoints(ReputationEventType eventType) {
        return pointsTable.getOrDefault(eventType, 0);
    }
}
