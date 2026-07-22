package elevator.request;

/**
 * A destination floor button pressed from inside a specific car. Already
 * tied to that elevator - never goes through ElevatorController's dispatch.
 */
public class CabinRequest extends Request {
    public CabinRequest(int destinationFloor) {
        super(destinationFloor);
    }
}
