package Strategy;

import java.time.LocalDateTime;

import model.Ticket;

public interface PricingStrategy {
    double calculatePrice(Ticket ticket, LocalDateTime exitTime);
}
