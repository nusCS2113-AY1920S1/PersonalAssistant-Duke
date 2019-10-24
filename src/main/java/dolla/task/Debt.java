package dolla.task;

import dolla.Time;
import dolla.task.Log;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Debt is a Class that stores an instance of the user's owe and borrow cases.
 */
public class Debt extends Log {

    protected String type;
    protected double amount;
    protected String saveType;
    protected LocalDate date;

    /**
     * Instantiates a new Debt.
     * @param type        Type of the debt (owe or borrow).
     * @param name        Name of the person who is related to this debt.
     * @param amount      Amount of money owed or borrowed.
     * @param description Description of the debt.
     * @param date        Due date of the debt.
     */
    public Debt(String type, String name, double amount, String description, LocalDate date) {
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.userInput = type + " " + name + " " + amount + " " + description + " " + "/due " + Time.dateToString(date);
    }

    /**
     * Returns a string to with information about the debt to be displayed
     * to the user.
     * @return String with information of debt
     */
    @Override
    public String getLogText() {
        return "[" + type + "] "
                + "[" + name + "] "
                + "[" + amountToMoney() + "] "
                + "[" + description + "] "
                + "[/due " + Time.dateToString(date) + "]";
    }

    /**
     * Returns a string with description about the description part of the debt.
     * @return String with description about the description part of the debt.
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Returns a string with the information about the name associated with the debt.
     * @return String with the information about the name associated with the debt.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns a LocalDate with information about the due date of the debt.
     * @return LocalDate with information about the due date of the debt.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns a string with information about the amount of the debt.
     * @return String with information about the amount of the debt.
     */
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
                + description + " | "
                + Time.dateToString(date);
    }

    /**
     * Returns a string with information about the user input.
     * @return String with information about the user input.
     */
    @Override
    public String getUserInput() {
        return userInput;
    }
}
