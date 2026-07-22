package elevator.state;

import elevator.model.Elevator;
import elevator.request.Request;

/**
 * The elevator has arrived at a floor to service a request: doors are open,
 * passengers are boarding/alighting. This simulation treats one stop as
 * taking exactly one tick - onEnter() does the boarding, move() decides
 * what happens next.
 */
public class StoppedState implements ElevatorState {

    @Override
    public void onEnter(Elevator elevator) {
        int currentFloor = elevator.getCurrentFloor();
        // "Open doors": clear every pending request for this floor,
        // regardless of where it sits in the queue (arrival order isn't
        // floor order).
        elevator.getRequestQueue().removeIf(request -> request.getFloor() == currentFloor);
    }

    @Override
    public void move(Elevator elevator) {
        if (elevator.hasPendingRequests()) {
            elevator.setState(new MovingState());
        } else {
            elevator.setState(new IdleState());
        }
    }

    @Override
    public void addRequest(Elevator elevator, Request request) {
        // A request for the floor we're currently stopped at would just sit
        // in the queue until the next stop under the current design; onEnter
        // already ran for this stop so it won't be serviced until the
        // elevator comes back around. Acceptable simplification for now.
        elevator.addRequest(request);
    }
}
