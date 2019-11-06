package money;

import java.text.DecimalFormat;

public class Item {
    private float price;
    private String description;

    DecimalFormat decimalFormat = new DecimalFormat("#.00");

    //@@author chengweixuan
    public Item(float price, String description) {
        this.price = price;
        this.description = description;
    }

    public float getPrice() {
        return this.price;
    }

    public String getPriceStr() {
        return decimalFormat.format(price);
    }

    public String getDescription() {
        return this.description;
    }

    public String toString() {
        return "$" + getPriceStr() + " " + description;
    }

}
