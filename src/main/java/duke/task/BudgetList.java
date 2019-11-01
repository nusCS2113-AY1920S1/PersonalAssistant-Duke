package duke.task;

import java.util.ArrayList;

//@@author maxxyx96
public class BudgetList {
    private static final int ZERO = 0;
    public static final String INITIAL_BUDGET = "0";
    private ArrayList<String> budgetList;

    /**
     * Creates an empty budget list using an array list if there are no
     * list of budget found.
     */
    public BudgetList() {
        budgetList = new ArrayList<>();
        budgetList.add(INITIAL_BUDGET);
    }

    /**
     * Creates an updated budget list with the specified array list.
     *
     * @param list The budget array list.
     */
    public BudgetList(ArrayList<String> list) {
        budgetList = list;
        if (getSize() < 1) {
            resetBudget(0);
        }
    }

    /**
     * Converts a String input from a string to a float.
     *
     * @param input the input to be converted.
     * @return the float equivalent of the string.
     */
    public float floatConverter(String input) {
        try {
            return Float.parseFloat(input);
        } catch (Exception e) {
            return 0;
        }

    }

    /**
     * Changes the budget by the amount stated.
     *
     * @param amount the amount to be added into the budget.
     * @param remark Some description.
     */
    public void addToBudget(String amount, String remark) {
        float currentBudget = floatConverter(budgetList.get(ZERO)) + floatConverter(amount);
        budgetList.add(amount + " : " + remark);
        budgetList.set(ZERO, Float.toString(currentBudget));
    }

    /**
     * Gets the current total budget that is stored in budgetList.
     *
     * @return returns the budget that is stored in budgetList.
     */
    public float getBudget() {
        return floatConverter(budgetList.get(ZERO));
    }

    /**
     * Resets the current budgetList with the amount stated.
     *
     * @param amount The budget amount that is to be reset to.
     */
    public void resetBudget(float amount) {
        String stringAmount = Float.toString(amount);
        budgetList.clear();
        budgetList.add(INITIAL_BUDGET);
        budgetList.set(ZERO, stringAmount);
    }

    /**
     * Get the size of the budget list.
     *
     * @return The size of the budget list.
     */
    public int getSize() {
        return budgetList.size();
    }

    /**
     * Get the list of budgets.
     *
     * @return The list of budget.
     */
    public ArrayList<String> getList() {
        return this.budgetList;
    }

    /**
     * Get the list of budgets.
     *
     * @return The list of budget.
     */
    public String getStringList() {
        if (budgetList.size() < 1) {
            return "     You have not made an entry yet.";
        } else {
            String listString = "     Here are your current expenses: \n";
            for (int i = 1; i < budgetList.size(); i++) {
                listString += "     " + (i) + ") " + budgetList.get(i) + "\n";
            }
            return listString;
        }

    }

    /**
     * Removes the stated index of budgetList from the list.
     *
     * @param index the index of the list to be removed.
     */
    public void removeEntryFromList(int index) {
        budgetList.remove(index);
    }

    /**
     * Undoes the last budget and update the budget value and the budget list.
     *
     * @param index The index of the budget to be undone.
     */
    public void undoLastBudget(int index) {
        float budget = getBudget();
        String undoBudget = budgetList.get(index);
        undoBudget = undoBudget.split(":")[ZERO];
        budget -= Float.parseFloat(undoBudget);
        removeEntryFromList(index);
        budgetList.set(ZERO, Float.toString(budget));
    }
}
//@@author