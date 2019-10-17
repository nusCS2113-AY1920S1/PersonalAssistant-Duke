package dolla.task;

import java.util.ArrayList;

/**
 * A class that contains methods regarding the Limit List.
 */
public class LimitList {

    protected ArrayList<Limit> BudgetList;
    protected ArrayList<Limit> SavingList;

    public LimitList(ArrayList<Limit> budgetList, ArrayList<Limit> savingList) {
        this.BudgetList = budgetList;
        this.SavingList = savingList;
    }

    /**
     * Method to search list for limits pertaining a certain input duration
     * @param list
     */
    public boolean limitFinder(ArrayList<Limit> list, Limit.Duration duration) {
        boolean limitIsFound = false;
        for (int i = 0; i < list.size(); i++) {
            Limit currLimit = list.get(i);
            if (duration == currLimit.duration) {
                limitIsFound = true;
            }
        }
        return limitIsFound;
    }

    //ADD TO LIST
    public void addToBudget(ArrayList<Limit> list, Limit.Duration duration, double amount) {

    }

    //REMOVE FROM LIST

    //EDIT LIST
}
