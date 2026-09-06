package state;

import entity.Item;
import enums.Coin;
import root.VendingMachine;

public class IdleState implements VendingMachineState {
    private final VendingMachine machine;

    public IdleState(VendingMachine machine) {
        this.machine = machine;
    }

    @Override
    public void insertCoin(Coin coin) {
        // Transition to MoneyEnteredState
        // Update balance
    }

    @Override
    public void selectItem(String itemCode) {
        // select item and transition to itemselected state
        Item item = machine.getInventory().getItem(itemCode);
        if (item != null && machine.getInventory().isAvailable(itemCode)) {
            machine.setState(new ItemSelectedState(machine, item));
        } else {
            // Handle item not available or invalid selection
        }
    }

    @Override
    public void dispense() {
        // Cannot dispense in IdleState
    }

    @Override
    public void refund() {
        // Cannot refund in IdleState
    }

    @Override
    public void returnChange() {
        // Cannot return change in IdleState
    }

}
