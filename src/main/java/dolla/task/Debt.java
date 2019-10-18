package dolla.task;

import dolla.Time;
import dolla.task.Log;

import java.time.LocalDateTime;

public class Debt extends Log {

    protected String type;
    protected double amount;
    protected String saveType;
    protected LocalDateTime date;

    public Debt(String type, String name, double amount, String description, LocalDateTime date) {
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    @Override
    public String getLogText() {
        return "[" + type + "] "
                + "[" + name + "] "
                + "[" + amountToMoney() + "] "
                + "[" + description + "]"
                + "[/due" + Time.dateTimeToString(date) + "]";
    }

    @Override
    public String getDescription() {
        return description;
    }

    public String amountToMoney() {
        return "$" + amount;
    }

    /**
     * Returns a string with information about the entry to be saved.
     * @return String with information of entry in save format.
     */
    @Override
    public String formatSave() {
        saveType = type.equals("owe") ? "O" : "B";
        return saveType + " | "
                + name + " | "
                + amount + " | "
                + description + "|"
                + Time.dateTimeToString(date);
    }
}
