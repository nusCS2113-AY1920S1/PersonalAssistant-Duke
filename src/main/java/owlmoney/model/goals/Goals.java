package owlmoney.model.goals;

import owlmoney.model.bank.Saving;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * Goals class for initialisation of Goals Object.
 */
public class Goals {
    private String name;
    private double amount;
    private Date date;
    private int days;

    /**
     * Creates an instance of Goals.
     *
     * @param name   The name used to understand the goal.
     * @param amount The target amount to reach for the goal.
     * @param date   The targeted date to meet the goal.
     */
    public Goals(String name, double amount, Date date) {
        this.name = name;
        this.amount = amount;
        this.date = date;
    }

    /**
     * Gets the name of the Goal.
     *
     * @return name of the Goal.
     */
    public String getGoalsName() {
        return this.name;
    }

    /**
     * Gets the amount of the Goal.
     *
     * @return amount of the Goal.
     */
    public double getGoalsAmount() {
        return this.amount;
    }

    /**
     * Gets the date of the Goal.
     *
     * @return date of the Goal.
     */
    public Date getGoalsDate() {
        return this.date;
    }

    /**
     * Gets the full details of the Goal.
     *
     * @return name, amount and date of goal in the corrected format.
     */
    protected String getGoalsDetails() {
        return "To accomplish: " + getGoalsName() + "\nAmount to save: $"
                + new DecimalFormat("0.00").format(getGoalsAmount()) + "\nBy: " + getGoalsDate();
    }

    /**
     * Sets the new name of the Goal.
     *
     * @param newName new name of the Goal.
     */
    void setGoalsName(String newName) {
        this.name = newName;
    }

    /**
     * Sets the new target amount for the Goal.
     *
     * @param newAmount new amount of the Goal.
     */
    void setGoalsAmount(double newAmount) {
        this.amount = newAmount;
    }

    /**
     * Sets the new target date for the Goal.
     *
     * @param newDate new date of the Goal.
     */
    void setGoalsDate(Date newDate) {
        this.date = newDate;
    }
}
