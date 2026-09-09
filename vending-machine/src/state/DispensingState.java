package state;

import entity.Item;
import enums.Coin;
import vendingmachine.VendingMachine;

public class DispensingState implements VendingMachineState {

    private final VendingMachine vendingMachine;
    private final int change;

    public DispensingState(VendingMachine vendingMachine, int change) {
        this.vendingMachine = vendingMachine;
        this.change = change;
    }

    @Override
    public void insertCoin(Coin coin) {
        throw new IllegalStateException(
            "Cannot insert coin while dispensing"
        );
    }

    @Override
    public void selectItem(String itemCode) {
        throw new IllegalStateException(
            "Cannot select an item while dispensing"
        );
    }

    @Override
    public void dispense() {

        String itemCode = vendingMachine.getSelectedItemCode();

        Item item = vendingMachine.getInventory().getItem(itemCode);

        if (item == null) {
            throw new IllegalStateException(
                "Selected item is no longer available"
            );
        }

        // Atomically commit the sale: fails if stock was depleted concurrently
        boolean dispensed = vendingMachine.getInventory().reduceStockIfAvailable(itemCode);
        if (!dispensed) {
            throw new IllegalStateException(
                "Selected item is no longer available"
            );
        }

        // Dispense item
        System.out.println("Dispensing: " + item.getName());

        // Return change
        if (change > 0) {
            System.out.println("Returning change: " + change + " cents");
        }

        // Clear current transaction
        vendingMachine.clearTransaction();

        // Return to idle
        vendingMachine.setState(new IdleState(vendingMachine));
    }

    @Override
    public void refund() {
        throw new IllegalStateException(
            "Cannot refund while dispensing"
        );
    }

    @Override
    public void returnChange() {
        // Change is handled as part of dispense()
        throw new IllegalStateException(
            "Change is returned during the dispensing process"
        );
    }
}