package owlmoney.model.goals;

import owlmoney.model.bank.Bank;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Contains the details for each goal.
 */
public class Goals {
    private String name;
    private double amount;
    private Date date;
    private Bank savingAccount = null;
    private boolean done = false;
    private boolean isAchieved = false;

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
        this.savingAccount = savingAcc;
        if (Double.parseDouble(getRemainingAmount()) <= 0) {
            this.done = true;
        }
    }

    /**
     * Converts date to number of days to check goal deadline.
     *
     * @return number of days left to goal deadline.
     */
    public int convertDateToDays() {
        long diffInMillies = Math.abs(this.date.getTime() - new Date().getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return (int) diff;
    }

    /**
     * Gets the name of the Goal.
     *
     * @return name of the Goal.
     */
    String getGoalsName() {
        return this.name;
    }

    /**
     * Gets the amount of the Goal.
     *
     * @return amount of the Goal.
     */
    double getGoalsAmount() {
        return this.amount;
    }

    /**
     * Gets the date of the Goal.
     *
     * @return date of the Goal.
     */
    String getGoalsDate() {
        DateFormat temp = new SimpleDateFormat("dd MMMM yyyy");
        return temp.format(this.date);
    }

    /**
     * Gets the date of the Goal in date format..
     *
     * @return date of the Goal in date format.
     */
    Date getGoalsDateInDateFormat() {
        return this.date;
    }

    /**
     * Gets the saving account name which is tied to Goal.
     *
     * @return name of Saving Account.
     */
    String getSavingAccount() {
        if (savingAccount == null) {
            return "-NOT TIED-";
        } else {
            return savingAccount.getAccountName();
        }
    }

    /**
     * Gets the remaining amount to save to reach the Goal.
     *
     * @return remaining amount left to reaching goal.
     */
    String getRemainingAmount() {
        if (savingAccount == null) {
            return new DecimalFormat("0.00").format(getGoalsAmount());
        } else {
            double remainingAmount = getGoalsAmount() - savingAccount.getCurrentAmount();
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
    void isDone(Double remainingAmount) {
        if (remainingAmount <= 0) {
            done = true;
        }
    }

    /**
     * Gets the status of Goal if achieved.
     *
     * @return Y / N if goal is achieved.
     */
    String getStatus() {
        return done ? "Y" : "N";
    }

    /**
     * Gets the status of Goal if achieved.
     *
     * @return status of goal in boolean
     */
    boolean getRawStatus() {
        return done;
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

    void setSavingAccount(Bank newSavingAcc) {
        this.savingAccount = newSavingAcc;
    }

    boolean getGoalAchievementStatus() {
        return this.isAchieved;
    }

    public void achieveGoal() {
        this.isAchieved = true;
    }

    double getSavingAmount() {
        return this.savingAccount.getCurrentAmount();
    }

    public void markDone() {
        done = true;
    }
}
