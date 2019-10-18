package moomoo.task;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Holds a map for the budget for each category.
 */
public class Budget {

    private HashMap<String, Double> categoryBudgets;
    private DecimalFormat df;

    public Budget() {
        this.categoryBudgets = new HashMap<String, Double>();
        df = new DecimalFormat("#.00");
    }

    /**
     * Takes in budget set by user and set budget variable.
     * Initializes DecimalFormat to force doubles to display with 2 decimal places.
     */
    public Budget(HashMap<String, Double> newBudget) {
        this.categoryBudgets = newBudget;
        df = new DecimalFormat("#.00");
    }

    @Override
    public String toString() {
        String returnVal = "";
        Iterator budgetIterator = categoryBudgets.entrySet().iterator();
        while (budgetIterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry) budgetIterator.next();
            returnVal += "Your budget for " + mapElement.getKey() + " is: $" + df.format(mapElement.getValue()) + "\n";
        }
        return returnVal;
    }

    public String toStringCategory(String category) {
        return "Your budget for " + category + " is: $" + df.format(this.categoryBudgets.get(category)) + "\n";
    }

    /**
     * Returns budget from corresponding category.
     *
     * @param category Category to get budget from.
     * @return The budget of the given category.
     */
    public double getBudgetFromCategory(String category) {
        if (this.categoryBudgets.containsKey(category)) {
            return this.categoryBudgets.get(category);
        } else {
            return 0;
        }
    }

    public void addNewBudget(String category, double budget) {
        this.categoryBudgets.put(category, budget);
    }

    public HashMap<String, Double> getBudget() {
        return this.categoryBudgets;
    }

    public int getBudgetSize() {
        return this.categoryBudgets.size();
    }

}