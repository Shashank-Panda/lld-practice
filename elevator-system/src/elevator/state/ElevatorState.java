package elevator.state;

import elevator.model.Elevator;
import elevator.request.Request;

public interface ElevatorState {

    /**
     * Called exactly once, right after Elevator.setState() switches into this
     * state. Put entry side-effects here (reset direction, open doors, start a
     * timer) so they live in one place instead of being duplicated at every
     * call site that transitions state.
     */
    void onEnter(Elevator elevator);

    /**
     * Advance the elevator by one simulation "tick" while in this state. Only
     * MovingState should actually change currentFloor; other states should
     * either no-op or use the tick to check a timer (e.g. door-open duration).
     */
    void move(Elevator elevator);

    /**
     * Handle a new pickup/destination request arriving while the elevator is
     * in this state. Each state decides: enqueue only, enqueue + transition,
     * or reject.
     */
    void addRequest(Elevator elevator, Request request);

    // No getDirection() here on purpose: Elevator.direction is the single
    // source of truth (see Elevator.getDirection()/setDirection()). A
    // per-state getDirection() couldn't be answered correctly anyway -
    // MovingState alone can mean UP or DOWN depending on the elevator asking.
}
