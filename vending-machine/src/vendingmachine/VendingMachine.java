package vendingmachine;

import entity.Inventory;
import entity.Item;
import state.IdleState;
import state.VendingMachineState;

public class VendingMachine {
    private final Inventory inventory = new Inventory();
    private VendingMachineState currentState;
    private int balance = 0;
    private String selectedItemCode;

    public VendingMachine() {
        // Initialize the vending machine with some items
        inventory.addItem("A1", new Item("Soda", "A1", 1.50), 10);
        inventory.addItem("B2", new Item("Chips", "B2", 1.00), 5);
        inventory.addItem("C3", new Item("Candy", "C3", 0.75), 20);
        this.currentState = new IdleState(this);
    }

    public void setState(VendingMachineState state) {
        this.currentState = state;
    }

    public void insertCoin(enums.Coin coin) {
        currentState.insertCoin(coin);
    }

    public void addItem(String sku, Item item, int quantity) {
        inventory.addItem(sku, item, quantity);
    }

    public void selectItem(String itemCode) {
        currentState.selectItem(itemCode);
    }

    public void dispense() {
        currentState.dispense();
    }

    public void refund() {
        currentState.refund();
    }

    public void returnChange() {
        currentState.returnChange();
    }

    public void addBalance(int amount) {
        balance += amount;
    }

    public int getBalance() {
        return balance;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public String getSelectedItemCode() {
        return selectedItemCode;
    }

    public void clearTransaction() {
        balance = 0;
        selectedItemCode = null;
    }
}
