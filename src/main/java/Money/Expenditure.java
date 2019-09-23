package Money;

import java.util.Date;

public class Expenditure extends Item {

    private String category;
    private Date boughtTime;

    public Expenditure(float price, String description, String category, Date boughtTime) {
        super(price, description);
        this.category = category;
        this.boughtTime = boughtTime;
    }

    public String getCategory() {
        return category;
    }

    public Date getBoughtTime() {
        return boughtTime;
    }
}
