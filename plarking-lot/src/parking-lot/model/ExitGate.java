package model;

import java.time.LocalDateTime;

import Strategy.PricingStrategy;

public class ExitGate {
    private int gateNum;
    private PricingStrategy pricingStrategy;

    ExitGate(int gateNum, PricingStrategy pricingStrategy) {
        this.gateNum = gateNum;
        this.pricingStrategy = pricingStrategy;
    }

    public Ticket processExit(Ticket ticket) {
        LocalDateTime exitTime = LocalDateTime.now();
        double amount = pricingStrategy.calculatePrice(ticket, exitTime);

        ticket.closeOut(exitTime, String.valueOf(this.gateNum), amount);
        ParkingLotController.getInstance().release(ticket.getVehicleType());

        return ticket;
    }
}
