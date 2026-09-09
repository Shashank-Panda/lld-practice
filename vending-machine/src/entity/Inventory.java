package entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Inventory {
    private final Map<String, Item> itemMap = new ConcurrentHashMap<>();
    private final Map<String, AtomicInteger> stockMap = new ConcurrentHashMap<>();

    public void addItem(String sku, Item item, int quantity) {
        itemMap.put(sku, item);
        stockMap.computeIfAbsent(sku, k -> new AtomicInteger(0)).addAndGet(quantity);
    }

    public Item getItem(String sku) {
        return itemMap.get(sku);
    }

    public boolean isAvailable(String sku) {
        AtomicInteger stock = stockMap.get(sku);
        return stock != null && stock.get() > 0;
    }

    public int getStockCount(String sku) {
        AtomicInteger stock = stockMap.get(sku);
        return stock == null ? 0 : stock.get();
    }

    public boolean reduceStockIfAvailable(String sku) {
        AtomicInteger stock = stockMap.get(sku);
        if (stock == null) {
            return false;
        }
        int current;
        do {
            current = stock.get();
            if (current <= 0) {
                return false;
            }
        } while (!stock.compareAndSet(current, current - 1));
        return true;
    }
}
