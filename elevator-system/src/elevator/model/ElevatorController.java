package elevator.model;

import java.util.List;
import java.util.NoSuchElementException;

import elevator.request.CabinRequest;
import elevator.request.HallRequest;
import elevator.strategy.DispatchStrategy;

/**
 * Owns the fleet - the only thing with a global view across elevators.
 * Responsible for assigning hall calls to a car (dispatch) and driving the
 * simulation loop. Does NOT decide how any individual elevator orders its
 * own stops - that's SchedulingStrategy's job, owned by Elevator itself.
 */
public class ElevatorController {
    private final List<Elevator> elevators;
    private final DispatchStrategy dispatchStrategy;

    public ElevatorController(List<Elevator> elevators, DispatchStrategy dispatchStrategy) {
        this.elevators = elevators;
        this.dispatchStrategy = dispatchStrategy;
    }

    /**
     * A hall call (someone at a floor wants to go UP/DOWN) - not tied to any
     * elevator yet, so it needs fleet-wide assignment.
     */
    public void submitHallRequest(HallRequest request) {
        Elevator elevator = dispatchStrategy.selectElevator(request, elevators);
        // Dispatch through the state, not the raw queue, so state-specific
        // transition logic (e.g. Idle -> Moving) actually runs.
        elevator.getState().addRequest(elevator, request);
    }

    /**
     * A cabin call (button pressed inside a specific car) - already tied to
     * one elevator, so it bypasses dispatchStrategy entirely.
     */
    public void submitCabinRequest(int elevatorId, CabinRequest request) {
        Elevator elevator = findElevatorById(elevatorId);
        elevator.getState().addRequest(elevator, request);
    }

    /** Advances the whole simulation by one tick. */
    public void tick() {
        for (Elevator elevator : elevators) {
            elevator.getState().move(elevator);
        }
    }

    private Elevator findElevatorById(int elevatorId) {
        return elevators.stream()
                .filter(elevator -> elevator.getId() == elevatorId)
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No elevator with id " + elevatorId));
    }
}
