package money;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Expenditure extends Item {

    private String category;
    private LocalDate boughtTime;
    private DateTimeFormatter dateTimeFormatter;

    /**
     * Constructor of the Expenditure Object to record expenditure.
     * @param price Price of the item spent on
     * @param description info on the item
     * @param category Category the item is grouped under
     * @param boughtTime Date which the item is bought
     */
    public Expenditure(float price, String description, String category, LocalDate boughtTime) {
        super(price, description);
        this.category = category;
        this.boughtTime = boughtTime;
        dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + "(on: " + getBoughtTime() + ")";
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getDateBoughtTime() {
        return boughtTime;
    }

    public String getBoughtTime() {
        return boughtTime.format(dateTimeFormatter);
    }
}
