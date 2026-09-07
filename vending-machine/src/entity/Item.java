package entity;

public class Item {
    private String name;
    private String code;
    private int priceInCents;

    public Item(String name, String code, int priceInCents) {
        this.code = code;
        this.name = name;
        this.priceInCents = priceInCents;
    }

    public String getName() {
        return name;
    }

    public int getPriceInCents() {
        return priceInCents;
    }
}
