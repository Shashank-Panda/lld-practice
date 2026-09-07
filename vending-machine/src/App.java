import enums.Coin;
import vendingmachine.VendingMachine;

public class App {
    public static void main(String[] args) throws Exception {

        System.out.println("=== Case 1: Exact payment ===");
        VendingMachine m1 = new VendingMachine();
        m1.selectItem("C3"); // Candy, 75 cents
        m1.insertCoin(Coin.QUARTER);
        m1.insertCoin(Coin.QUARTER);
        m1.insertCoin(Coin.QUARTER);
        m1.dispense();

        System.out.println("\n=== Case 2: Overpayment / change returned ===");
        VendingMachine m2 = new VendingMachine();
        m2.addItem("D4", new entity.Item("Gum", "D4", 90), 5); // non-round price to force overshoot
        m2.selectItem("D4");
        m2.insertCoin(Coin.QUARTER); // 25
        m2.insertCoin(Coin.QUARTER); // 50
        m2.insertCoin(Coin.QUARTER); // 75, still short of 90
        m2.insertCoin(Coin.QUARTER); // 100, overshoots 90 by 10 -> triggers dispensing
        m2.dispense();

        System.out.println("\n=== Case 3: Underpayment then top-up ===");
        VendingMachine m3 = new VendingMachine();
        m3.selectItem("A1"); // Soda, 150 cents
        m3.insertCoin(Coin.QUARTER); // 25 cents, insufficient, stays in MoneyEnteredState
        m3.insertCoin(Coin.QUARTER); // 50 cents, still insufficient
        m3.insertCoin(Coin.QUARTER);
        m3.insertCoin(Coin.QUARTER);
        m3.insertCoin(Coin.QUARTER);
        m3.insertCoin(Coin.QUARTER); // 150 cents total, now sufficient
        m3.dispense();

        System.out.println("\n=== Case 4: Refund mid-transaction ===");
        VendingMachine m4 = new VendingMachine();
        m4.selectItem("A1"); // Soda, 150 cents
        m4.insertCoin(Coin.QUARTER);
        m4.insertCoin(Coin.QUARTER); // 50 cents inserted, not enough
        m4.refund();

        System.out.println("\n=== Case 5: Invalid item code ===");
        VendingMachine m5 = new VendingMachine();
        try {
            m5.selectItem("Z9");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }

        System.out.println("\n=== Case 6: Out-of-stock item ===");
        VendingMachine m6 = new VendingMachine();
        for (int i = 0; i < 5; i++) {
            m6.selectItem("B2");
            m6.insertCoin(Coin.QUARTER);
            m6.insertCoin(Coin.QUARTER);
            m6.insertCoin(Coin.QUARTER);
            m6.insertCoin(Coin.QUARTER);
            m6.dispense();
        }
        try {
            m6.selectItem("B2"); // stock is now 0
        } catch (IllegalStateException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }

        System.out.println("\n=== Case 7: Invalid operation for current state ===");
        VendingMachine m7 = new VendingMachine();
        try {
            m7.dispense(); // nothing selected, still Idle
        } catch (IllegalStateException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }
        try {
            m7.insertCoin(Coin.QUARTER); // no item selected yet
        } catch (IllegalStateException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }
    }
}

/*
1. The vending machine should support multiple products with different prices and quantities.
2. The machine should accept coins and notes of different denominations.
3. The machine should dispense the selected product and return change if necessary.
4. The machine should keep track of the available products and their quantities.
5. The machine should handle multiple transactions concurrently and ensure data consistency.
6. The machine should provide an interface for restocking products and collecting money.
7. The machine should handle exceptional scenarios, such as insufficient funds or out-of-stock products
*/
