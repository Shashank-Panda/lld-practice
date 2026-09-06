package entity;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private final Map<String, Item> itemMap = new HashMap<>();
    private final Map<String, Integer> stockMap = new HashMap<>();

    public void addItem(String sku, Item item, int quantity) {
        itemMap.put(sku, item);
        stockMap.put(sku, quantity);
    }

    public Item getItem(String sku) {
        return itemMap.get(sku);
    }

    public boolean isAvailable(String sku) {
        return stockMap.getOrDefault(sku, 0) > 0;
    }

    public void reduceStock(String sku) {
        stockMap.put(sku, stockMap.get(sku)-1);
    }
}
