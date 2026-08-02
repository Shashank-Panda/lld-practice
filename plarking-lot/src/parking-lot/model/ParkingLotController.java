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
    private PricingStrategy pricingStrategy;
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

        this.pricingStrategy = pricingStrategy;
        initialized = true;
    }

    public synchronized void addVehicleType(VehicleType type, int capacity) {
        requireInitialized();
        capacities.merge(type, capacity, Integer::sum);
        occupied.putIfAbsent(type, 0);
    }

    public synchronized EntryGate addEntryGate() {
        requireInitialized();
        EntryGate gate = new EntryGate(entryGates.size() + 1);
        entryGates.add(gate);
        return gate;
    }

    public synchronized ExitGate addExitGate() {
        requireInitialized();
        ExitGate gate = new ExitGate(exitGates.size() + 1, pricingStrategy);
        exitGates.add(gate);
        return gate;
    }

    private void requireInitialized() {
        if (!initialized) {
            throw new IllegalStateException("ParkingLotController has not been initialized.");
        }
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
