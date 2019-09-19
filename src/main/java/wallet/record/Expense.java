package wallet.record;

public class Expense extends Record {
    private double amount;
    private String category;
    private boolean isRecurring;

    /**
     * Constructs the Expense object.
     *
     * @param amount      The amount of expense.
     * @param category    The category of the expense.
     * @param isRecurring Whether the expense is recurring.
     */
    public Expense(double amount, String category, boolean isRecurring) {
        this.amount = amount;
        this.category = category;
        this.isRecurring = isRecurring;
    }

    /**
     * Returns the amount of the expense.
     *
     * @return The amount of expense.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the expense.
     *
     * @param amount The amount of expense.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Returns the category of the expense.
     *
     * @return The category of the expense.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the Category of the expense.
     *
     * @param category The category of the expense.
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Returns whether the expense is recurring.
     *
     * @return true or false - Whether the expense is recurring.
     */
    public boolean isRecurring() {
        return isRecurring;
    }

    /**
     * Sets whether the expense is recurring.
     *
     * @param recurring Whether the expense is recurring.
     */
    public void setRecurring(boolean recurring) {
        isRecurring = recurring;
    }


}
