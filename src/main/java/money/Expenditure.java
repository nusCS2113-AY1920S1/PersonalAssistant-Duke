package money;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Expenditure extends Item {

    private String category;
    private Date boughtTime;
    private SimpleDateFormat simpleDateFormat;

    /**
     * Constructor of the Expenditure Object to record expenditure.
     * @param price Price of the item spent on
     * @param description info on the item
     * @param category Category the item is grouped under
     * @param boughtTime Date which the item is bought
     */
    public Expenditure(float price, String description, String category, Date boughtTime) {
        super(price, description);
        this.category = category;
        this.boughtTime = boughtTime;
        simpleDateFormat  = new SimpleDateFormat("d/M/yyyy");
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + "(on: " + getBoughtTime() + ")";
    }

    public String getCategory() {
        return category;
    }

    public Date getDateBoughtTime() {
        return boughtTime;
    }

    public String getBoughtTime() {
        return simpleDateFormat.format(boughtTime);
    }
}
