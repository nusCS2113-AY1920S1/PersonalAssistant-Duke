package dolla.task;

import dolla.Time;

public class Shortcut extends Record {


    public Shortcut(String type, double amount, String description) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.recordType = RECORD_SHORTCUT;
    }

    @Override
    public String amountToMoney() {
        return "$" + amount;
    }

    @Override
    public String getRecordDetail() {
        return "[" + "shortcut" + "] "
                + "[" + type + "] "
                + "[" + amountToMoney() + "] "
                + "[" + description + "]";
    }

    @Override
    public String formatSave() {
        return  "shortcut" + " | "
                + type + " | "
                + amount + " | "
                + description;
    }
}
