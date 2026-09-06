package state;

import entity.Item;
import enums.Coin;
import vendingmachine.VendingMachine;

public class ItemSelectedState implements VendingMachineState {

    private final VendingMachine vendingMachine;
    private final Item item;

    public ItemSelectedState(VendingMachine vendingMachine, Item item) {
        this.vendingMachine = vendingMachine;
        this.item = item;
    }

    @Override
    public void insertCoin(Coin coin) {
        vendingMachine.addBalance(coin.getValue());
        vendingMachine.setState(new MoneyEnteredState(vendingMachine));
    }

    @Override
    public void selectItem(String itemCode) {
        throw new IllegalStateException(
                "An item has already been selected");
    }

    @Override
    public void dispense() {
        throw new IllegalStateException(
                "Cannot dispense before inserting money");
    }

    @Override
    public void refund() {
        throw new IllegalStateException(
                "No money has been inserted");
    }

    @Override
    public void returnChange() {
        throw new IllegalStateException(
                "No change to return");
    }
}
