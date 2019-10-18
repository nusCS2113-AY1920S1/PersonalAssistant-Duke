package dolla.task;

import dolla.task.Log;

public class Debt extends Log {

    protected String type;
    protected double amount;
    protected String saveType;

    public Debt(String type, String name, double amount, String description) {
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.userInput = type + " " + name + " " + amount + " " + description;
    }

    @Override
    public String getLogText() {
        return "[" + type + "] "
                + "[" + name + "] "
                + "[" + amountToMoney() + "] "
                + "[" + description + "]";
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
                + description;
    }

    @Override
    public String getUserInput() {
        return userInput;
    }
}
