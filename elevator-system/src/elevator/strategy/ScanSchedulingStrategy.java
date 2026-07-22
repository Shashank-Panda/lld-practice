package elevator.strategy;

import elevator.enums.Direction;
import elevator.model.Elevator;
import elevator.request.Request;

/**
 * Classic SCAN/LOOK: keep moving in the current direction, servicing every
 * pending floor along the way, and only reverse once nothing is left ahead
 * in that direction.
 */
public class ScanSchedulingStrategy implements SchedulingStrategy {

    @Override
    public Integer getNextStopFloor(Elevator elevator) {
        int currentFloor = elevator.getCurrentFloor();
        Direction direction = elevator.getDirection();

        Integer nearestAhead = null;
        Integer nearestBehind = null;

        for (Request request : elevator.getRequestQueue()) {
            int floor = request.getFloor();
            if (floor == currentFloor) {
                continue;
            }
            boolean isAhead = direction == Direction.DOWN ? floor < currentFloor : floor > currentFloor;
            if (isAhead) {
                if (nearestAhead == null || Math.abs(floor - currentFloor) < Math.abs(nearestAhead - currentFloor)) {
                    nearestAhead = floor;
                }
            } else {
                if (nearestBehind == null || Math.abs(floor - currentFloor) < Math.abs(nearestBehind - currentFloor)) {
                    nearestBehind = floor;
                }
            }
        }

        // Prefer continuing in the current direction; only reverse if
        // there's nothing left ahead.
        return nearestAhead != null ? nearestAhead : nearestBehind;
    }
}
