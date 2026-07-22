package elevator.state;

import elevator.enums.Direction;
import elevator.model.Elevator;
import elevator.request.Request;

/**
 * Out of service - entered by an external override (e.g. an operator
 * forcing elevator.setState(new MaintenanceState())), independent of the
 * normal Idle -> Moving -> Stopped cycle. Pending requests are preserved
 * rather than discarded: whoever ends maintenance by transitioning back to
 * IdleState will find IdleState.onEnter() automatically resumes them.
 */
public class MaintenanceState implements ElevatorState {

    @Override
    public void onEnter(Elevator elevator) {
        elevator.setDirection(Direction.IDLE);
        elevator.setTargetFloor(null);
    }

    @Override
    public void move(Elevator elevator) {
        // No-op: out of service, doesn't serve requests while here.
    }

    @Override
    public void addRequest(Elevator elevator, Request request) {
        // Still accept and queue the request so it isn't lost - just don't
        // act on it until maintenance ends.
        elevator.addRequest(request);
    }
}
