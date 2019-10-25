package owlmoney.model.goals;

import owlmoney.model.bank.Bank;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Contains the details for each goal.
 */
public class Goals {
    private String name;
    private double amount;
    private Date date;
    private Bank savingAcc = null;
    private boolean done = false;

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
     * Creates an instance of Goals which is tied to a saving account.
     *
     * @param name   The name used to understand the goal.
     * @param amount The target amount to reach for the goal.
     * @param date   The targeted date to meet the goal.
     * @param savingAcc The saving account used to track goal progress.
     */
    public Goals(String name, double amount, Date date, Bank savingAcc) {
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.savingAcc = savingAcc;
        if (Double.parseDouble(getRemainingAmount()) <= 0) {
            this.done = true;
        }
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
    public String getGoalsDate() {
        DateFormat temp = new SimpleDateFormat("dd MMMM yyyy");
        return temp.format(this.date);
    }

    /**
     * Gets the saving account name which is tied to Goal.
     *
     * @return name of Saving Account.
     */
    public String getSavingAcc() {
        if (savingAcc == null) {
            return "";
        } else {
            return savingAcc.getAccountName();
        }
    }

    /**
     * Gets the remaining amount to save to reach the Goal.
     *
     * @return remaining amount left to reaching goal.
     */
    public String getRemainingAmount() {
        if (savingAcc == null) {
            return "";
        } else {
            double remainingAmount = getGoalsAmount() - savingAcc.getCurrentAmount();
            if (remainingAmount <= 0) {
                return "0.00";
            } else {
                return new DecimalFormat("0.00").format(remainingAmount);
            }
        }
    }

    /**
     * Marks if Goal is achieved.
     *
     * @param remainingAmount amount remaining to reach goal.
     */
    public void isDone(Double remainingAmount) {
        if (remainingAmount <= 0) {
            done = true;
        }
    }

    /**
     * Gets the status of Goal if achieved.
     *
     * @return tick / cross if goal is achieved.
     */
    public String getStatus() {
        return done ? "✓" : "✘";
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

    /**
     * Sets the new saving account for Goal.
     *
     * @param newSavingAcc new saving account to tie to Goal.
     */
    void setSavingAcc(Bank newSavingAcc) {
        this.savingAcc = newSavingAcc;
    }
}
