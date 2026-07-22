package elevator.state;

import elevator.enums.Direction;
import elevator.model.Elevator;
import elevator.request.Request;

/**
 * The elevator has at least one target floor and is actively travelling
 * (up or down - direction lives on Elevator, not on this class) towards it.
 */
public class MovingState implements ElevatorState {

    @Override
    public void onEnter(Elevator elevator) {
        Integer nextStop = elevator.getNextStopFloor();
        if (nextStop == null) {
            // Nothing pending after all (e.g. the only request was cleared
            // elsewhere) - nothing to travel towards.
            elevator.setState(new IdleState());
            return;
        }
        elevator.setTargetFloor(nextStop);
        // Keep direction consistent with the target - matters on the
        // reversal case, where SchedulingStrategy hands back a floor behind
        // the elevator's current direction of travel.
        if (nextStop > elevator.getCurrentFloor()) {
            elevator.setDirection(Direction.UP);
        } else if (nextStop < elevator.getCurrentFloor()) {
            elevator.setDirection(Direction.DOWN);
        }
    }

    @Override
    public void move(Elevator elevator) {
        elevator.moveOneFloor();
        Integer targetFloor = elevator.getTargetFloor();
        if (targetFloor != null && elevator.getCurrentFloor() == targetFloor) {
            elevator.setState(new StoppedState());
        }
    }

    @Override
    public void addRequest(Elevator elevator, Request request) {
        elevator.addRequest(request);
        // Re-evaluate the nearest stop immediately: if this request is
        // closer (in the current direction) than the floor we were already
        // headed to, SchedulingStrategy will hand back the new, nearer
        // floor instead - without this, the elevator would sail past an
        // en-route request and only pick it up after reversing.
        elevator.setTargetFloor(elevator.getNextStopFloor());
    }
}
