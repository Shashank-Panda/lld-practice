package Strategy;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;

import enums.VehicleType;
import model.Ticket;

public class HourlyPricingStrategy implements PricingStrategy {
    private final Map<VehicleType, Double> hourlyRates;

    public HourlyPricingStrategy(Map<VehicleType, Double> hourlyRates) {
        this.hourlyRates = new EnumMap<>(hourlyRates);
    }

    @Override
    public double calculatePrice(Ticket ticket, LocalDateTime exitTime) {
        Double rate = hourlyRates.get(ticket.getVehicleType());
        if (rate == null) {
            throw new IllegalStateException("No hourly rate configured for " + ticket.getVehicleType());
        }

        long minutesParked = Duration.between(ticket.getEntryTime(), exitTime).toMinutes();
        long billableHours = Math.max(1, (long) Math.ceil(minutesParked / 60.0));

        return billableHours * rate;
    }
}
