package dolla.model;

import dolla.Time;

import java.time.LocalDate;

import static dolla.parser.ParserStringList.DEBT_COMMAND_OWE;

//@@author tatayu
/**
 * Debt is a Class that stores an instance of the user's owe and borrow cases.
 */
public class Debt extends Record {

    protected String saveType;

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
        this.recordType = RECORD_DEBT;
        this.userInput = type + " " + name + " " + amount + " " + description + " /due "
                       + Time.dateToString(date);
    }

    /**
     * Returns a string to with information about the debt to be displayed
     * to the user.
     * @return String with information of debt
     */
    @Override
    public String getRecordDetail() {
        return "[" + type + "] "
                + "[" + name + "] "
                + "[" + amountToMoney() + "] "
                + "[" + description + "] "
                + "[/due " + Time.dateToString(date) + "]";
    }

    @Override
    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns a string with information about the debt to be saved.
     * @return String with information of debt in save format.
     */
    @Override
    public String formatSave() {
        saveType = DEBT_COMMAND_OWE.equals(type) ? "O" : "B";
        return saveType + " | "
                + name + " | "
                + amount + " | "
                + description + " | "
                + Time.dateToString(date);
    }

    @Override
    public String getUserInput() {
        return userInput;
    }
}
