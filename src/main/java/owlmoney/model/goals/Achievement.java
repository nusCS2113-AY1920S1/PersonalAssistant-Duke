package owlmoney.model.goals;

/**
 * Contains details for each achievement.
 */
public class Achievement {
    private String name;
    private double amount;
    private String category;
    private String date;

    /**
     * Creates instance of achievement.
     *
     * @param name Goal Name.
     * @param amount Goal Target Amount.
     * @param category Currently is GOALS.
     * @param date Goal targeted date.
     */
    Achievement(String name, double amount, String category, String date) {
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    /**
     * Gets achievement name.
     *
     * @return name of achievement.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets category for achievement.
     *
     * @return category for achievement.
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * Gets amount for achievement.
     *
     * @return amount for achievement.
     */
    public double getAmount() {
        return this.amount;
    }

    /**
     * Gets date for achievement.
     *
     * @return date for achievement.
     */
    public String getDate() {
        return this.date;
    }
}
