import java.util.EnumMap;
import java.util.Map;

import Strategy.HourlyPricingStrategy;
import Strategy.PricingStrategy;
import enums.VehicleType;
import model.EntryGate;
import model.ExitGate;
import model.ParkingLotController;
import model.Ticket;

public class App {
    public static void main(String[] args) throws Exception {
        Map<VehicleType, Integer> capacities = new EnumMap<>(VehicleType.class);
        capacities.put(VehicleType.CAR, 2);
        capacities.put(VehicleType.MOTORCYCLE, 2);
        capacities.put(VehicleType.TRUCK, 1);

        Map<VehicleType, Double> rates = new EnumMap<>(VehicleType.class);
        rates.put(VehicleType.CAR, 20.0);
        rates.put(VehicleType.MOTORCYCLE, 10.0);
        rates.put(VehicleType.TRUCK, 35.0);

        PricingStrategy pricingStrategy = new HourlyPricingStrategy(rates);

        ParkingLotController controller = ParkingLotController.getInstance();
        controller.initialize(capacities, 2, 2, pricingStrategy);

        // Exercise the new expansion methods.
        controller.addVehicleType(VehicleType.TRUCK, 1);
        EntryGate thirdEntryGate = controller.addEntryGate();
        ExitGate thirdExitGate = controller.addExitGate();
        System.out.println("Added entry gate #" + controller.getEntryGates().indexOf(thirdEntryGate));
        System.out.println("Added exit gate #" + controller.getExitGates().indexOf(thirdExitGate));

        EntryGate entryGate1 = controller.getEntryGates().get(0);
        Ticket ticket = entryGate1.processEntry("KA-01-1234", VehicleType.CAR);
        System.out.println("Issued ticket: " + ticket.getTicketId()
                + " entryGate=" + ticket.getEntryGateId()
                + " entryTime=" + ticket.getEntryTime());

        Thread.sleep(1000);

        ExitGate exitGate1 = controller.getExitGates().get(0);
        Ticket closedTicket = exitGate1.processExit(ticket);
        System.out.println("Closed ticket: " + closedTicket.getTicketId()
                + " exitGate=" + closedTicket.getExitGateId()
                + " amountCharged=" + closedTicket.getAmountCharged()
                + " status=" + closedTicket.getTicketStatus());

        System.out.println();
        System.out.println("--- Negative path: lot full ---");
        // TRUCK capacity is 1 (initial) + 1 (addVehicleType) = 2.
        EntryGate truckEntryGate = controller.getEntryGates().get(0);
        Ticket truckTicket1 = truckEntryGate.processEntry("KA-02-1111", VehicleType.TRUCK);
        truckEntryGate.processEntry("KA-02-2222", VehicleType.TRUCK);
        try {
            truckEntryGate.processEntry("KA-02-3333", VehicleType.TRUCK);
            System.out.println("FAIL: expected lot-full rejection, but entry succeeded");
        } catch (IllegalStateException e) {
            System.out.println("PASS: lot-full correctly rejected -> " + e.getMessage());
        }

        System.out.println();
        System.out.println("--- Negative path: double exit on same ticket ---");
        ExitGate truckExitGate = controller.getExitGates().get(0);
        truckExitGate.processExit(truckTicket1);
        try {
            truckExitGate.processExit(truckTicket1);
            System.out.println("FAIL: expected double-exit rejection, but exit succeeded");
        } catch (IllegalStateException e) {
            System.out.println("PASS: double-exit correctly rejected -> " + e.getMessage());
        }

        System.out.println();
        System.out.println("--- Negative path: double initialize ---");
        try {
            controller.initialize(capacities, 2, 2, pricingStrategy);
            System.out.println("FAIL: expected double-initialize rejection, but it succeeded");
        } catch (IllegalStateException e) {
            System.out.println("PASS: double-initialize correctly rejected -> " + e.getMessage());
        }
    }
}

// 1. The parking lot should have multiple levels, each level with a certain number of parking spots.
// 2. The parking lot should support different types of vehicles, such as cars, motorcycles, and trucks.
// 3. Each parking spot should be able to accommodate a specific type of vehicle.
// 4. The system should assign a parking spot to a vehicle upon entry and release it when the vehicle exits.
// 5. The system should track the availability of parking spots and provide real-time information to customers.
// 6. The system should handle multiple entry and exit points and support concurrent access.


/*
Requirements
1. # parking lots = 1
2. # Floor levels = 3
3. # spots per level = 150
4. # vehicle types = 3 (car, motorcycle, truck) -> enum
5. # number of entry points = 2
6. # number of exit points = 2
7. # parking spot types = 3 (compact, regular, large) -> enum
8.
*/

/*
Entities:
1. ParkingLot/ParkingLotController
2. Floor
3. entry gate - ticket generator
4. exit gate - ticket validator and calls to payment service
5. parking ticket
6. pricing strategy
*/
