package dolla.task;

import dolla.Time;
import dolla.task.Log;
import dolla.Ui;
//import dolla.ui.LimitUi;

/**
 * Limit is a class that stores all limit related methods (savings and budgets)
 */
public class Limit extends Log {
    protected char sign; // '+' for saving, '-' for budget
    protected LimitType type;
    protected double amount;
    protected Duration duration;
    protected LimitList limitList;
    protected String saveType;

    public enum LimitType {
        SAVING, BUDGET
    }

    public enum Duration {
        DAILY, WEEKLY, MONTHLY
    }

    /**
     * Creates an instance of Limit.
     * @param type Budget or Saving.
     * @param amount Amount of money to be limited.
     * @param duration Duration of the limit.
     */
    public Limit(LimitType type, double amount, Duration duration) {
        this.sign = (type.equals(LimitType.SAVING) ? '+' : '-');
        this.type = type;
        this.amount = amount;
        this.duration = duration;
    }

    /**
     * Returns a string to with information about the limit to be displayed
     * to the user.
     * @return String with information of limit.
     */
    @Override
    public String getLogText() {
        return "[" + type + "] "
                + "[" + amountToMoney() + "] "
                + "[/for " + duration + "]";
    }

    public String amountToMoney() {
        return "$" + amount;
    }

//    /**
//     * Method to add a new limit.
//     * @param type Budget or Saving
//     * @param amount Amount to be limited.
//     * @param duration Duration of limit.
//     */
//    public void AddLimit(String type, double amount, Duration duration) {
//        if (type.equals(LimitType.BUDGET)) {
//            AddBudget(amount, duration);
//        } else if (type.equals(LimitType.SAVING)) {
//            //AddSaving(amount, duration);
//        } else {
//            Ui.printErrorMsg();
//        }
//    }

//    public void AddBudget(double amount, Duration duration) {
//        boolean alreadyExist = limitList.limitFinder(limitList.BudgetList, duration);
//
//        if (alreadyExist) {
////            LimitUi.existingLimitPrinter(LimitType.BUDGET);
//        } else {
//
//        }
//    }
//
//    public void EditCurrBudget() {
//        //edit budget after adding in expenses or income
//    }

    //remove

    @Override
    public String formatSave() {
        saveType = type.equals("saving") ? "S" : "BU";
        return  saveType + " | "
                + amount + " | "
                + duration;
    }
}
