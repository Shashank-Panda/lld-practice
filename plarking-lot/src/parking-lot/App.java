public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
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
