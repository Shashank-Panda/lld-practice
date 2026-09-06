package state;

import entity.Item;
import enums.Coin;
import root.VendingMachine;

public class ItemSelectedState implements VendingMachineState {
    private final VendingMachine vendingMachine;
    private final Item item;

    public ItemSelectedState(VendingMachine vendingMachine, Item item) {
        this.vendingMachine = vendingMachine;
        this.item = item;
    }
    
    @Override
    public void insertCoin(Coin coin) {
        // Implementation for inserting coin in ItemSelectedState
    }

    @Override
    public void selectItem(String itemCode) {
        // Implementation for selecting item in ItemSelectedState
    }

    @Override
    public void dispense() {
        // Implementation for dispensing item in ItemSelectedState
    }

    @Override
    public void refund() {
        // Implementation for refunding in ItemSelectedState
    }

    @Override
    public void returnChange() {
        // Implementation for returning change in ItemSelectedState
    }

}
