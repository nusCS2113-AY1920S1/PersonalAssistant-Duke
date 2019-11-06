package dolla.model;

import dolla.Time;

import java.time.LocalDate;

/**
 * Entry is a Class that stores an instance of the user's expense or income.
 */
public class Entry extends Record {
    protected char sign; // '+' for income, '-' for expense
    protected String saveType;

    /**
     * Creates an instance of Entry.
     * @param type Income or Expense.
     * @param amount Amount of money that is earned/spent.
     * @param description Details pertaining to the entry.
     * @param date Date of income/expense.
     * @param tagName Details of the tag name.
     */
    public Entry(String type, double amount, String description, LocalDate date, String tagName) {
        this.sign = ("income".equals(type) ? '+' : '-');
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.tagName = tagName;
        this.recordType = RECORD_ENTRY;
        this.userInput = type + " " + amount + " " + description + " /on " + Time.dateToString(date) + " " + tagName;
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
    public String getRecordDetail() {
        return "[" + type + "] "
                + "[" + amountToMoney() + "] "
                + "[" + description + "] "
                + "[/on " + Time.dateToString(date) + "]"
                + " {Tag: " + tagName + '}';
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
                + Time.dateToString(date) + " | "
                + tagName;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public String getUserInput() {
        return userInput;
    }
}
