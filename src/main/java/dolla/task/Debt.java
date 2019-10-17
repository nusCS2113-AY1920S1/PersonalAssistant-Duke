package dolla.task;

import dolla.Log;

public class Debt extends Log {

    protected String type;
    protected String name;
    protected double amount;
    protected String description;
    protected String saveType;

    public Debt(String type, String name, double amount, String description) {
        this.type = type;
        this.name = name;
        this.amount = amount;
        this.description = description;
    }

    @Override
    public String getLogText() {
        return "[" + type + "] "
                + "[" + name + "] "
                + "[" + amountToMoney() + "] "
                + "[" + description + "]";
    }

    public String amountToMoney() {
        return "$" + amount;
    }

    public String formatSave() {
        saveType = type.equals("owe") ? "O" : "B";
        return saveType + " | "
                + name + " | "
                + amount + " | "
                + description;
    }
}
