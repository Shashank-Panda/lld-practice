package model;

import java.time.LocalDateTime;

import enums.TicketStatus;
import enums.VehicleType;

public class Ticket {
    private final String ticketId;
    private final String vehicleNumber;
    private final VehicleType vehicleType;
    private final LocalDateTime entryTime;
    private final String entryGateId;

    private LocalDateTime exitTime;
    private String exitGateId;
    private double amountCharged;
    private TicketStatus ticketStatus;

    private Ticket(Builder builder) {
        this.ticketId = builder.ticketId;
        this.vehicleNumber = builder.vehicleNumber;
        this.vehicleType = builder.vehicleType;
        this.entryTime = builder.entryTime;
        this.entryGateId = builder.entryGateId;
        this.ticketStatus = TicketStatus.ACTIVE;
    }

    public void closeOut(LocalDateTime exitTime, String exitGateId, double amountCharged) {
        if (this.ticketStatus == TicketStatus.PAID) {
            throw new IllegalStateException("Ticket " + ticketId + " is already closed.");
        }
        this.exitTime = exitTime;
        this.exitGateId = exitGateId;
        this.amountCharged = amountCharged;
        this.ticketStatus = TicketStatus.PAID;
    }

    public String getTicketId() {
        return ticketId;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public String getEntryGateId() {
        return entryGateId;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public String getExitGateId() {
        return exitGateId;
    }

    public double getAmountCharged() {
        return amountCharged;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public static class Builder {
        private String ticketId;
        private String vehicleNumber;
        private VehicleType vehicleType;
        private LocalDateTime entryTime;
        private String entryGateId;

        public Builder ticketId(String ticketId) {
            this.ticketId = ticketId;
            return this;
        }

        public Builder vehicleNumber(String vehicleNumber) {
            this.vehicleNumber = vehicleNumber;
            return this;
        }

        public Builder vehicleType(VehicleType vehicleType) {
            this.vehicleType = vehicleType;
            return this;
        }

        public Builder entryTime(LocalDateTime entryTime) {
            this.entryTime = entryTime;
            return this;
        }

        public Builder entryGateId(String entryGateId) {
            this.entryGateId = entryGateId;
            return this;
        }

        public Ticket build() {
            if (ticketId == null || vehicleNumber == null || vehicleType == null
                    || entryTime == null || entryGateId == null) {
                throw new IllegalStateException("Missing required fields to build Ticket.");
            }
            return new Ticket(this);
        }
    }
}
