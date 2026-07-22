package elevator.strategy;

import elevator.model.Elevator;

/**
 * Decides, for a single elevator, which floor it should head to next given
 * its own current floor, direction, and pending requests. Owned/used by the
 * Elevator itself (see Elevator.getNextStopFloor()) - has no visibility into
 * other elevators. Contrast with DispatchStrategy, which is the
 * ElevatorController-level concern of picking *which* elevator handles a
 * hall call in the first place.
 */
public interface SchedulingStrategy {

    /**
     * @return the next floor this elevator should stop at, or null if it has
     *         nothing pending.
     */
    Integer getNextStopFloor(Elevator elevator);
}
