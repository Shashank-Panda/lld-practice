package elevator.model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import elevator.enums.Direction;
import elevator.request.Request;
import elevator.state.ElevatorState;
import elevator.state.IdleState;
import elevator.strategy.ScanSchedulingStrategy;
import elevator.strategy.SchedulingStrategy;

public class Elevator {
    private final int id;
    private final AtomicInteger currentFloor;
    private volatile Direction direction;
    private volatile ElevatorState state;
    private volatile Integer targetFloor;
    private final BlockingQueue<Request> requestQueue;
    private final SchedulingStrategy schedulingStrategy;

    public Elevator(int id, int startingFloor) {
        this(id, startingFloor, new ScanSchedulingStrategy());
    }

    public Elevator(int id, int startingFloor, SchedulingStrategy schedulingStrategy) {
        this.id = id;
        this.currentFloor = new AtomicInteger(startingFloor);
        this.direction = Direction.IDLE;
        this.targetFloor = null;
        this.requestQueue = new LinkedBlockingQueue<>();
        this.schedulingStrategy = schedulingStrategy;
        // Assigned last: IdleState.onEnter(this) may read the fields above.
        this.state = new IdleState();
        this.state.onEnter(this);
    }

    public int getId() {
        return id;
    }

    public int getCurrentFloor() {
        return currentFloor.get();
    }

    public Direction getDirection() {
        return direction;
    }

    public ElevatorState getState() {
        return state;
    }

    public void addRequest(Request request) {
        requestQueue.offer(request);
    }

    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }

    /**
     * Look at the next request without removing it. NOTE: requestQueue is
     * FIFO, which is not the same as "next floor along the current
     * direction" - real elevator scheduling (SCAN/LOOK) needs requests
     * ordered by floor, not arrival order. Fine as a placeholder; revisit
     * once SchedulingStrategy exists.
     */
    public Request peekNextRequest() {
        return requestQueue.peek();
    }

    public Request pollNextRequest() {
        return requestQueue.poll();
    }

    /** Delegates to this elevator's own SchedulingStrategy - see MovingState/StoppedState. */
    public Integer getNextStopFloor() {
        return schedulingStrategy.getNextStopFloor(this);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setState(ElevatorState state) {
        this.state = state;
        // Centralized here (rather than at every call site that transitions
        // state) so entry side-effects can never be forgotten.
        state.onEnter(this);
    }

    public Integer getTargetFloor() {
        return targetFloor;
    }

    public void setTargetFloor(Integer targetFloor) {
        this.targetFloor = targetFloor;
    }

    public void moveOneFloor() {
        if (direction == Direction.UP) {
            currentFloor.incrementAndGet();
        } else if (direction == Direction.DOWN) {
            currentFloor.decrementAndGet();
        }
    }

    public BlockingQueue<Request> getRequestQueue() {
        return requestQueue;
    }
}
