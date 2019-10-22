//@@author matthewng1996

package wallet.model.record;

public class Budget {

    private double amount;
    private int month;
    private int year;

    /**
     * Constructs the Budget object.
     * @param amount amount of budget set.
     * @param month month which budget is set to.
     * @param year year which budget is set to.
     */
    public Budget(double amount, int month, int year) {
        this.amount = amount;
        this.month = month;
        this.year = year;
    }

    /**
     * Returns the amount of the budget.
     *
     * @return The amount of budget.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the budget.
     *
     * @param amount The amount of budget.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Returns the month of the budget.
     *
     * @return The month of budget.
     */
    public int getMonth() {
        return month;
    }

    /**
     * Sets the month of the budget.
     *
     * @param month The month of budget.
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * Returns the year of the budget.
     *
     * @return The year of budget.
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the year of the budget.
     *
     * @param year The year of budget.
     */
    public void setYear(int year) {
        this.year = year;
    }

    public String writeToFile() {
        return amount + "," + month + "," + year;
    }
}
