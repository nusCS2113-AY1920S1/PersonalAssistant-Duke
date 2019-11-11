//@@author matthewng1996

package wallet.model.record;

public class Budget {

    private double amount;
    private int month;
    private int year;
    private boolean expenseTakenIntoAccount;
    private double accountedExpenseAmount;

    /**
     * Constructs the Budget object.
     * @param amount amount of budget set.
     * @param month month which budget is set to.
     * @param year year which budget is set to.
     */
    public Budget(double amount, int month, int year, boolean expenseTakenIntoAccount, double accountedExpenseAmount) {
        this.amount = amount;
        this.month = month;
        this.year = year;
        this.expenseTakenIntoAccount = expenseTakenIntoAccount;
        this.accountedExpenseAmount = accountedExpenseAmount;
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
     * Gets the boolean value of whether the budget has taken existing expenses into account.
     * @return boolean value of existing budget with existing expenses
     */
    public boolean getExpenseTakenIntoAccount() {
        return expenseTakenIntoAccount;
    }

    /**
     * Sets the boolean of the budget with exsiting expenses.
     *
     * @param expenseTakenIntoAccount The boolean value of budget with existing expenses accounted for.
     */
    public void setExpenseTakenIntoAccount(boolean expenseTakenIntoAccount) {
        this.expenseTakenIntoAccount = expenseTakenIntoAccount;
    }

    /**
     * Gets the amount of accounted expenses based on boolean value of Budget.
     * @return accounted expenses amount to be added into existing budget
     */
    public double getAccountedExpenseAmount() {
        return accountedExpenseAmount;
    }

    /**
     * Sets the amount of accounted expenses.
     *
     * @param accountedExpenseAmount The accounted expenses amount to be added into existing budget
     */
    public void setAccountedExpenseAmount(double accountedExpenseAmount) {
        this.accountedExpenseAmount = accountedExpenseAmount;
    }


    public String writeToFile() {
        return amount + "," + month + "," + year + "," + expenseTakenIntoAccount + "," + accountedExpenseAmount;
    }
}
