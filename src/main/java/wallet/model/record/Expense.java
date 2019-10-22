package wallet.model.record;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The Expense Class.
 */
public class Expense extends Record {
    private double amount;
    private Category category;
    private boolean isRecurring;
    private String recFrequency;

    /**
     * Default constructor.
     */
    public Expense() {
        this.recFrequency = "";
    }

    /**
     * Constructs the Expense object.
     *
     * @param amount      The amount of expense.
     * @param category    The category of the expense.
     * @param isRecurring Whether the expense is recurring.
     */
    public Expense(String description, LocalDate date, double amount,
                   Category category, boolean isRecurring, String recFrequency) {
        super(description, date);
        this.amount = amount;
        this.category = category;
        this.isRecurring = isRecurring;
        this.recFrequency = recFrequency;
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
    public Category getCategory() {
        return category;
    }

    /**
     * Sets the Category of the expense.
     *
     * @param category The category of the expense.
     */
    public void setCategory(Category category) {
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

    /**
     * Gets the recurring frequency string.
     *
     * @return A string indicating the recurring frequency.
     */
    public String getRecFrequency() {
        return recFrequency;
    }

    /**
     * Sets the recurring frequency.
     *
     * @param recFrequency The recurring frequency string.
     */
    public void setRecFrequency(String recFrequency) {
        this.recFrequency = recFrequency;
    }

    @Override
    public String toString() {
        if (isRecurring) {
            return "[ID: " + getId() + "]" + "[" + recFrequency + "] "
                    + getDescription() + " Amount:$" + amount + " Date:"
                    + DateTimeFormatter.ofPattern("dd MMM yyyy").format(getDate()) + " Category:" + category;
        } else {
            return "[ID: " + getId() + "]" + " "
                    + getDescription() + " Amount:$" + amount + " Date:"
                    + DateTimeFormatter.ofPattern("dd MMM yyyy").format(getDate()) + " Category:" + category;
        }
    }


    @Override
    public String writeToFile() {
        if (isRecurring) {
            return getId() + "," + getDescription() + "," + amount + ","
                    + DateTimeFormatter.ofPattern("dd/MM/yyyy").format(getDate()) + "," + category + "," + recFrequency;
        } else {
            return getId() + "," + getDescription() + "," + amount + ","
                    + DateTimeFormatter.ofPattern("dd/MM/yyyy").format(getDate()) + "," + category;
        }
    }
}
