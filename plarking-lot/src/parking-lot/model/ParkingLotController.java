package model;

import java.util.EnumMap;
import java.util.List;

import enums.VehicleType;

public class ParkingLotController {
    private EnumMap<VehicleType, Integer> capacities;
    private EnumMap<VehicleType, Integer> occupied;
    private List<EntryGate> entryGates;
    private List<ExitGate> exitGates;

    private ParkingLotController() {
        this.capacities = new EnumMap<>(VehicleType.class);
        this.occupied = new EnumMap<>(VehicleType.class);
    }

    private static class Holder {
        private static final ParkingLotController INSTANCE = new ParkingLotController();
    }

    public static ParkingLotController getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized boolean tryPark(VehicleType type) {
        Integer cap = capacities.get(type);
        if (cap == null || occupied.get(type) >= cap)
            return false;
        occupied.merge(type, 1, Integer::sum);
        return true;
    }

    public synchronized void release(VehicleType type) {
        occupied.merge(type, -1, Integer::sum);
    }
}
