package state;

import entity.Item;
import enums.Coin;
import vendingmachine.VendingMachine;

public class IdleState implements VendingMachineState {
    private final VendingMachine machine;

    public IdleState(VendingMachine machine) {
        this.machine = machine;
    }

    @Override
    public void insertCoin(Coin coin) {
        throw new IllegalStateException(
        "Cannot insert coin before selecting an item"
    );
    }

    @Override
    public void selectItem(String itemCode) {
                Item item = machine.getInventory().getItem(itemCode);

        if (item == null) {
            throw new IllegalArgumentException(
                "Invalid item code: " + itemCode
            );
        }

        if (!machine.getInventory().isAvailable(itemCode)) {
            throw new IllegalStateException(
                "Item is out of stock: " + itemCode
            );
        }

        machine.setState(new ItemSelectedState(machine, item));
    }

    @Override
    public void dispense() {
        throw new IllegalStateException(
            "Cannot dispense while machine is idle"
        );
    }

    @Override
    public void refund() {
        throw new IllegalStateException(
            "Cannot refund while machine is idle"
        );
    }

    @Override
    public void returnChange() {
        throw new IllegalStateException(
            "Cannot return change while machine is idle"
        );
    }

}
