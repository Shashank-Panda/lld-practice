package elevator.strategy;

import java.util.List;

import elevator.model.Elevator;
import elevator.request.HallRequest;

/**
 * Decides which elevator should be assigned a new hall call. Needs the full
 * fleet - this is an ElevatorController-level concern, never something an
 * individual Elevator should need to know about. Contrast with
 * SchedulingStrategy, which is the per-elevator concern of ordering that
 * car's own pending stops.
 */
public interface DispatchStrategy {
    Elevator selectElevator(HallRequest request, List<Elevator> elevators);
}
