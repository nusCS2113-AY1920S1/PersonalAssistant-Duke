package dolla.model;

/**
 * Limit is a class that stores all limit related methods (savings and budgets).
 */
public class Limit extends Record {

    protected String type;
    protected String duration;
    protected String saveType;

    /**
     * Creates an instance of Limit.
     * @param type Budget or Saving.
     * @param amount Amount of money to be limited.
     * @param duration Duration of the limit.
     */
    public Limit(String type, double amount, String duration) {
        this.type = type;
        this.amount = amount;
        this.duration = duration;
        this.recordType = RECORD_LIMIT;
    }

    /**
     * Returns a string to with information about the limit to be displayed
     * to the user.
     * @return String with information of limit.
     */
    public String getRecordDetail() {
        return "[" + type + "] "
                + "[" + amountToMoney() + "] "
                + "[" + duration + "]";
    }

    @Override
    public String formatSave() {
        saveType = type.equals("saving") ? "S" : "BU";
        return  saveType + " | "
                + amount + " | "
                + duration;
    }

    public String amountToMoney() {
        return "$" + amount;
    }

    public String getType() {
        return type;
    }

    public String getDuration() {
        return duration;
    }
}
