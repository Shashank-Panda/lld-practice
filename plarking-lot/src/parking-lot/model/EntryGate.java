package model;

import java.time.LocalDateTime;
import java.util.UUID;

import enums.VehicleType;

public class EntryGate {
    private int gateNum;

    EntryGate(int gateNum) {
        this.gateNum = gateNum;
    }

    public Ticket processEntry(String vehicleNumber, VehicleType type) {
        ParkingLotController controller = ParkingLotController.getInstance();
        if (!controller.tryPark(type)) {
            throw new IllegalStateException("Lot full for " + type);
        }

        return new Ticket.Builder()
                .ticketId(UUID.randomUUID().toString())
                .vehicleNumber(vehicleNumber)
                .vehicleType(type)
                .entryTime(LocalDateTime.now())
                .entryGateId(String.valueOf(this.gateNum))
                .build();
    }
}
