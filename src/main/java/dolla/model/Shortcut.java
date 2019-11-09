package dolla.model;

//@@author yetong1895
/**
 * Shortcut is a Class that stores an instance of the user's stored shortcuts of entry input.
 */
public class Shortcut extends Record {

    /**
     * Creates an instance of Shortcut.
     * @param type the type of entry
     * @param amount the amount in the entry
     * @param description the description in the entry
     */
    public Shortcut(String type, double amount, String description) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.recordType = RECORD_SHORTCUT;
        this.userInput = type + " " + amount + " " + description;
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

    @Override
    public String getUserInput() {
        return userInput;
    }
}
