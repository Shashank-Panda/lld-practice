package state;

import entity.Item;
import enums.Coin;
import vendingmachine.VendingMachine;

public class MoneyEnteredState implements VendingMachineState {
    private final VendingMachine vendingMachine;

    public MoneyEnteredState(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void insertCoin(Coin coin) {
        vendingMachine.addBalance(coin.getValue());
        String itemCode = vendingMachine.getSelectedItemCode();
        Item item = vendingMachine.getInventory().getItem(itemCode);

        if (vendingMachine.getBalance() >= item.getPrice()) {
            double change = vendingMachine.getBalance() - item.getPrice();
            vendingMachine.setState(new DispensingState(vendingMachine, change));
        }
    }

    @Override
    public void selectItem(String itemCode) {
        throw new IllegalStateException(
                "Cannot select another item after money has been inserted");
    }

    @Override
    public void dispense() {
        throw new IllegalStateException(
                "Cannot dispense before sufficient money has been inserted");
    }

    @Override
    public void refund() {
        // Money has been inserted, so refund is valid.
        vendingMachine.returnChange();
        vendingMachine.setState(new IdleState(vendingMachine));
    }

    @Override
    public void returnChange() {
        throw new IllegalStateException(
                "Change can only be returned after a successful purchase");
    }
}
