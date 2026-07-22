package elevator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import elevator.enums.Direction;
import elevator.model.Elevator;
import elevator.model.ElevatorController;
import elevator.request.CabinRequest;
import elevator.request.HallRequest;
import elevator.strategy.ScanDispatchStrategy;

public class App {
    public static void main(String[] args) {
        List<Elevator> elevators = new ArrayList<>(Arrays.asList(
                new Elevator(1, 0),
                new Elevator(2, 5)));
        ElevatorController controller = new ElevatorController(elevators, new ScanDispatchStrategy());

        Scanner scanner = new Scanner(System.in);
        printHelp();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split("\\s+");
            String command = parts[0].toLowerCase(Locale.ROOT);

            try {
                switch (command) {
                    case "hall":
                        controller.submitHallRequest(new HallRequest(
                                Integer.parseInt(parts[1]),
                                Direction.valueOf(parts[2].toUpperCase(Locale.ROOT))));
                        break;
                    case "cabin":
                        controller.submitCabinRequest(
                                Integer.parseInt(parts[1]),
                                new CabinRequest(Integer.parseInt(parts[2])));
                        break;
                    case "tick":
                        int ticks = parts.length > 1 ? Integer.parseInt(parts[1]) : 1;
                        for (int i = 0; i < ticks; i++) {
                            controller.tick();
                        }
                        printStatus(elevators);
                        break;
                    case "status":
                        printStatus(elevators);
                        break;
                    case "help":
                        printHelp();
                        break;
                    case "quit":
                    case "exit":
                        scanner.close();
                        return;
                    default:
                        System.out.println("Unknown command: " + command + " (type 'help' for a list)");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void printStatus(List<Elevator> elevators) {
        for (Elevator elevator : elevators) {
            System.out.printf("Elevator %d -> floor %d, dir %s, state %s%n",
                    elevator.getId(), elevator.getCurrentFloor(), elevator.getDirection(),
                    elevator.getState().getClass().getSimpleName());
        }
    }

    private static void printHelp() {
        System.out.println("Commands:");
        System.out.println("  hall <floor> <UP|DOWN>      - submit a hall call");
        System.out.println("  cabin <elevatorId> <floor>  - submit a cabin request");
        System.out.println("  tick [n]                    - advance the simulation by n ticks (default 1)");
        System.out.println("  status                      - print current elevator states");
        System.out.println("  help                        - show this message");
        System.out.println("  quit / exit                 - stop");
    }
}
