package elevator.strategy;

import java.util.List;

import elevator.enums.Direction;
import elevator.model.Elevator;
import elevator.request.HallRequest;
import elevator.state.MaintenanceState;

/**
 * Picks the elevator that's cheapest to reach the hall call: distance to the
 * caller's floor, penalized if the elevator isn't already heading that way
 * in the requested direction. A simplified version of the classic
 * "collective control" dispatch heuristic - not a full SCAN-path
 * simulation, but reuses the same idea of preferring a car already moving
 * towards the request over one that would have to turn around.
 */
public class ScanDispatchStrategy implements DispatchStrategy {

    private static final int WRONG_DIRECTION_PENALTY = 1000;

    @Override
    public Elevator selectElevator(HallRequest request, List<Elevator> elevators) {
        Elevator best = null;
        int bestCost = Integer.MAX_VALUE;

        for (Elevator elevator : elevators) {
            if (elevator.getState() instanceof MaintenanceState) {
                continue;
            }
            int cost = estimateCost(elevator, request);
            if (cost < bestCost) {
                bestCost = cost;
                best = elevator;
            }
        }

        if (best == null) {
            throw new IllegalStateException("No elevator available to service " + request);
        }
        return best;
    }

    private int estimateCost(Elevator elevator, HallRequest request) {
        int distance = Math.abs(elevator.getCurrentFloor() - request.getFloor());
        Direction direction = elevator.getDirection();

        if (direction == Direction.IDLE) {
            return distance;
        }

        boolean headingTowardCaller = direction == Direction.UP
                ? request.getFloor() >= elevator.getCurrentFloor()
                : request.getFloor() <= elevator.getCurrentFloor();
        boolean sameDirectionAsCaller = direction == request.getDirection();

        if (headingTowardCaller && sameDirectionAsCaller) {
            return distance;
        }
        return distance + WRONG_DIRECTION_PENALTY;
    }
}
