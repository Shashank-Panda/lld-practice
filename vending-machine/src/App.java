import java.util.concurrent.atomic.AtomicInteger;

import enums.Coin;
import entity.Item;
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

        System.out.println("\n=== Case 8: Concurrent purchases racing for limited stock ===");
        VendingMachine m8 = new VendingMachine();
        m8.addItem("F6", new Item("Gum", "F6", 75), 3); // only 3 units, 8 threads will race for them

        int threadCount = 8;
        AtomicInteger successCount = new AtomicInteger();
        Thread[] buyers = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            buyers[i] = new Thread(() -> {
                while (true) {
                    try {
                        m8.selectItem("F6");
                    } catch (IllegalStateException e) {
                        if (e.getMessage() != null && e.getMessage().startsWith("Item is out of stock")) {
                            return; // no stock left, give up
                        }
                        Thread.yield(); // another session is active, retry shortly
                        continue;
                    }
                    m8.insertCoin(Coin.QUARTER);
                    m8.insertCoin(Coin.QUARTER);
                    m8.insertCoin(Coin.QUARTER);
                    m8.dispense();
                    successCount.incrementAndGet();
                    return;
                }
            });
        }
        for (Thread t : buyers) t.start();
        for (Thread t : buyers) t.join();
        System.out.println("Successful purchases: " + successCount.get() + " (expected 3)");
        System.out.println("F6 stock remaining: " + m8.getInventory().getStockCount("F6") + " (expected 0)");

        System.out.println("\n=== Case 9: Restocking concurrently with an in-flight purchase (different item) ===");
        VendingMachine m9 = new VendingMachine(); // A1 (Soda) starts at 10 units
        Thread purchaser = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                m9.selectItem("C3"); // Candy, unrelated SKU to the one being restocked
                m9.insertCoin(Coin.QUARTER);
                m9.insertCoin(Coin.QUARTER);
                m9.insertCoin(Coin.QUARTER);
                m9.dispense();
            }
        });
        Thread restocker = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                m9.addItem("A1", new Item("Soda", "A1", 150), 2); // add 2 units, 5 times
            }
        });
        purchaser.start();
        restocker.start();
        purchaser.join();
        restocker.join();
        System.out.println("A1 stock after concurrent restock: " + m9.getInventory().getStockCount("A1") + " (expected 20 = 10 + 5*2)");
        System.out.println("C3 stock after concurrent purchases: " + m9.getInventory().getStockCount("C3") + " (expected 17 = 20 - 3)");
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
