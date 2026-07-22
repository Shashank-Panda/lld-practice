package elevator.state;

import elevator.enums.Direction;
import elevator.model.Elevator;
import elevator.request.Request;

/**
 * The elevator has no pending requests and is parked at its current floor.
 */
public class IdleState implements ElevatorState {

    @Override
    public void onEnter(Elevator elevator) {
        elevator.setDirection(Direction.IDLE);
        elevator.setTargetFloor(null);
        if (elevator.hasPendingRequests()) {
            // Requests were already queued when we arrived here (e.g.
            // resuming from MaintenanceState) - don't just sit here, go
            // service them.
            dispatch(elevator, elevator.peekNextRequest());
        }
    }

    @Override
    public void move(Elevator elevator) {
        // No-op: nothing to move towards while idle.
    }

    @Override
    public void addRequest(Elevator elevator, Request request) {
        elevator.addRequest(request);
        dispatch(elevator, request);
    }

    private void dispatch(Elevator elevator, Request request) {
        if (request.getFloor() == elevator.getCurrentFloor()) {
            // Already at the requested floor - no need to move at all.
            elevator.setState(new StoppedState());
            return;
        }
        Direction direction = request.getFloor() > elevator.getCurrentFloor() ? Direction.UP : Direction.DOWN;
        elevator.setDirection(direction);
        elevator.setState(new MovingState());
    }
}
