package moomoo.stubs;

import moomoo.feature.Budget;
import java.util.HashMap;

public class BudgetStub extends Budget {
    private HashMap<String, Double> categoryBudgets;

    public BudgetStub(HashMap<String, Double> newBudget) {
        this.categoryBudgets = newBudget;
    }

    /**
     * Returns budget from corresponding category.
     *
     * @param category Category to get budget from.
     * @return The budget of the given category.
     */
    @Override
    public double getBudgetFromCategory(String category) {
        if (this.categoryBudgets.containsKey(category)) {
            return this.categoryBudgets.get(category);
        } else {
            return 0;
        }
    }

}
