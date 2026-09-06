package entity;

public class Item {
    private String name;
    private String code;
    private double price;

    public Item(String name, String code, double price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
