package vendingmachine;

import entity.Inventory;
import entity.Item;
import state.IdleState;
import state.VendingMachineState;

public class VendingMachine {
    private final Inventory inventory = new Inventory();
    private final Object lock = new Object();
    private VendingMachineState currentState;
    private int balance = 0;
    private String selectedItemCode;

    public VendingMachine() {
        // Initialize the vending machine with some items
        inventory.addItem("A1", new Item("Soda", "A1", 150), 10);
        inventory.addItem("B2", new Item("Chips", "B2", 100), 5);
        inventory.addItem("C3", new Item("Candy", "C3", 75), 20);
        this.currentState = new IdleState(this);
    }

    public void setState(VendingMachineState state) {
        synchronized (lock) {
            this.currentState = state;
        }
    }

    public void insertCoin(enums.Coin coin) {
        synchronized (lock) {
            currentState.insertCoin(coin);
        }
    }

    public void addItem(String sku, Item item, int quantity) {
        inventory.addItem(sku, item, quantity);
    }

    public void selectItem(String itemCode) {
        synchronized (lock) {
            currentState.selectItem(itemCode);
        }
    }

    public void dispense() {
        synchronized (lock) {
            currentState.dispense();
        }
    }

    public void refund() {
        synchronized (lock) {
            currentState.refund();
        }
    }

    public void returnChange() {
        synchronized (lock) {
            currentState.returnChange();
        }
    }

    public void addBalance(int amount) {
        synchronized (lock) {
            balance += amount;
        }
    }

    public int getBalance() {
        synchronized (lock) {
            return balance;
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public String getSelectedItemCode() {
        synchronized (lock) {
            return selectedItemCode;
        }
    }

    public void setSelectedItemCode(String selectedItemCode) {
        synchronized (lock) {
            this.selectedItemCode = selectedItemCode;
        }
    }

    public void clearTransaction() {
        synchronized (lock) {
            balance = 0;
            selectedItemCode = null;
        }
    }
}
