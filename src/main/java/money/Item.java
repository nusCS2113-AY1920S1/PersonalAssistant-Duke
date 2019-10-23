package money;

public class Item {
    private float price;
    private String description;

    //@@author chengweixuan
    public Item(float price, String description) {
        this.price = price;
        this.description = description;
    }

    public float getPrice() {
        return this.price;
    }

    public String getDescription() {
        return this.description;
    }

    public String toString() {
        return "$" + price + " " + description;
    }

}
