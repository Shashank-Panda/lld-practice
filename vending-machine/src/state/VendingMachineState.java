package state;

import enums.Coin;

public interface VendingMachineState {
    void insertCoin(Coin coin);
    void selectItem(String itemCode);
    void dispense();
    void refund();
    void returnChange();
}
