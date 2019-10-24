package dolla.task;

import dolla.Time;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entry is a Class that stores an instance of the user's expense or income.
 */
public class Entry extends Log {
    protected char sign; // '+' for income, '-' for expense
    protected String type;
    protected String saveType;
    protected double amount;

    /**
     * Creates an instance of Entry.
     * @param type Income or Expense.
     * @param amount Amount of money that is earned/spent.
     * @param description Details pertaining to the entry.
     * @param date Date of income/expense.
     */
    public Entry(String type, double amount, String description, LocalDate date) {
        this.sign = (type.equals("income") ? '+' : '-');
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.userInput = type + " " + amount + " " + description + " " + "/on " + Time.dateToString(date);
    }

    public String amountToMoney() {
        return "$" + amount;
    }


    /**
     * Returns a string to with information about the entry to be displayed
     * to the user.
     * @return String with information of entry.
     */
    @Override
    public String getLogText() {
        return "[" + type + "] "
                + "[" + amountToMoney() + "] "
                + "[" + description + "] "
                + "[/on " + Time.dateToString(date) + "]";
    }

    /**
     * Returns a string with information about the entry to be saved.
     * @return String with information of entry in saving format.
     */
    @Override
    public String formatSave() {
        saveType = type.equals("income") ? "I" : "E";
        return  saveType + " | "
                + amount + " | "
                + description + " | "
                + Time.dateToString(date);
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getUserInput() {
        return userInput;
    }
}
