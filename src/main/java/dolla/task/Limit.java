package dolla.task;

import dolla.Ui;

/**
 * Limit is a class that stores all limit related methods (savings and budgets)
 */
public class Limit extends Log {

    protected String type;
    protected double amount;
    protected String duration;
    protected String saveType;

    String[] LimitType = {"saving", "budget"};
    String[] Duration = {"day", "week", "month"};

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
    }

    /**
     * Returns a string to with information about the limit to be displayed
     * to the user.
     * @return String with information of limit.
     */
    public String getLogText() {
        return "[" + type + "] "
                + "[" + amountToMoney() + "] "
                + "[/every " + duration + "]";
    }

    @Override
    public String getDescription() {
        return null; //to be modified
    }

    public String amountToMoney() {
        return "$" + amount;
    }

    /**
     * Method to add a new limit.
     * @param limit limit to be added to the limitList.
     * @param limitList list in which limit is to be added to.
     */

    public void AddLimit(Limit limit, LimitList limitList) {
        if (limit.type.equals(LimitType[0]) || limit.type.equals(LimitType[1])) {
            //dosmth
            //check if limit exists in limitlist
        } else {
            Ui.printErrorMsg();
        }
    }


    public void EditCurrLimit() {
        //edit budget after adding in expenses or income
    }

    @Override
    public String formatSave() {
        saveType = type.equals("saving") ? "S" : "BU";
        return  saveType + " | "
                + amount + " | "
                + duration;
    }
}
