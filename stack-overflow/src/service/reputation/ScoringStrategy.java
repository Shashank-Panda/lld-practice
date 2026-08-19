package service.reputation;

public interface ScoringStrategy {
    int getPoints(ReputationEventType eventType);
}
