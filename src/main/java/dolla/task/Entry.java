package dolla.task;

import dolla.Time;

import java.time.LocalDateTime;

/**
 * Entry is a Class that stores an instance of the user's expense or income.
 */
public class Entry {
    protected char sign; // '+' for income, '-' for expense
    protected String type;
    protected double amount;
    protected String description;
    protected LocalDateTime date;

    public Entry(String type, double amount, String description, LocalDateTime date) {
        this.sign = (type.equals("income") ? '+' : '-');
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    /**
     * Returns a string to with information about the entry to be displayed
     * to the user.
     * @return String with information of entry.
     */
    public String getEntryText() {
        return "[" + type + "] "
                + "[" + amountToMoney() + "] "
                + "[" + description + "] "
                + "[/on " + Time.dateTimeToString(date) + "]";
    }

    public String amountToMoney() {
        return "$" + amount;
    }

}
