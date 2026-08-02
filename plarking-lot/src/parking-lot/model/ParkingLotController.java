package model;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import enums.VehicleType;
import Strategy.PricingStrategy;

public class ParkingLotController {
    private EnumMap<VehicleType, Integer> capacities;
    private EnumMap<VehicleType, Integer> occupied;
    private List<EntryGate> entryGates;
    private List<ExitGate> exitGates;
    private boolean initialized;

    private ParkingLotController() {
        this.capacities = new EnumMap<>(VehicleType.class);
        this.occupied = new EnumMap<>(VehicleType.class);
        this.entryGates = new ArrayList<>();
        this.exitGates = new ArrayList<>();
    }

    private static class Holder {
        private static final ParkingLotController INSTANCE = new ParkingLotController();
    }

    public static ParkingLotController getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized void initialize(Map<VehicleType, Integer> capacities, int numEntryGates,
            int numExitGates, PricingStrategy pricingStrategy) {
        if (initialized) {
            throw new IllegalStateException("ParkingLotController is already initialized.");
        }

        this.capacities.putAll(capacities);
        for (VehicleType type : capacities.keySet()) {
            this.occupied.put(type, 0);
        }

        for (int i = 1; i <= numEntryGates; i++) {
            entryGates.add(new EntryGate(i));
        }
        for (int i = 1; i <= numExitGates; i++) {
            exitGates.add(new ExitGate(i, pricingStrategy));
        }

        initialized = true;
    }

    public List<EntryGate> getEntryGates() {
        return entryGates;
    }

    public List<ExitGate> getExitGates() {
        return exitGates;
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
