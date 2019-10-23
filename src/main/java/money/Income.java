package money;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Income extends Item {
    private LocalDate payday;
    private DateTimeFormatter dateTimeFormatter;

    /**
     * The constructor for the Income Object to record income sources.
     * @param price Money in from the income source
     * @param description info of the income source
     * @param payday Date which the income is received
     */
    //@@author {chengweixuan}
    public Income(float price, String description, LocalDate payday) {
        super(price, description);
        this.payday = payday;
        dateTimeFormatter  = DateTimeFormatter.ofPattern("d/M/yyyy");
    }

    public LocalDate getPayday() {
        return payday;
    }

    @Override
    public String toString() {
        return "[I]" + " " + super.getDescription() + "(salary: $" + super.getPrice() + ") (Paid On: "
                + getPaidTime() + ")";
    }

    public String getPaidTime() {
        return payday.format(dateTimeFormatter);
    }
}
