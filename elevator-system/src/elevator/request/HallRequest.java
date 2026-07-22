package elevator.request;

import elevator.enums.Direction;

/**
 * A call button pressed on a floor - "I'm at this floor and want to go
 * UP/DOWN". Not tied to any elevator yet; ElevatorController's
 * DispatchStrategy decides which car handles it.
 */
public class HallRequest extends Request {
    private final Direction direction;

    public HallRequest(int floor, Direction direction) {
        super(floor);
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
