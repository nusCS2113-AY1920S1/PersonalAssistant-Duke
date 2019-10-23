package money;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Bill extends Expenditure {

    private LocalDate nextPayDay;

    //@@author {Chianhaoplanks}
    public Bill(float price, String description, String category, LocalDate boughtDate, LocalDate nextPayDay) {
        super(price, description, category, boughtDate);
        this.nextPayDay = nextPayDay;
        dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
    }

    @Override
    public String toString() { return "[BILLS]" + " $" + getPrice() + " " +
            getDescription() + "(last paid: " + getBoughtDate() + ") " + "(next payday: " +
            getNextPayDay() + ")"; }

    public LocalDate getDateNextPayDay() { return nextPayDay; }

    public String getNextPayDay() { return nextPayDay.format(dateTimeFormatter); }

    public void setNextPayDay() { nextPayDay.plusMonths(1); }

}
