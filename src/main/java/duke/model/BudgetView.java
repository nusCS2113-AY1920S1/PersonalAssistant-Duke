package duke.model;

import java.util.Map;

public class BudgetView {

    /**
     * Maps the category set for the view
     */
    private Map<Integer, String> budgetViewCategory;

    /**
     * Constructor for Budget Object.
     * @param budgetViewCategory A map of view to the category
     */
    public BudgetView(Map<Integer, String> budgetViewCategory) {
        this.budgetViewCategory = budgetViewCategory;
    }

    /**
     * Sets category to a given view.
     *
     * @param view a Integer view to set
     * @param category the String tag specified that we want to set a view to
     */
    public void setBudgetView(int view, String category) {
        budgetViewCategory.put(view, category);
    }

    public Map<Integer, String> getBudgetViewCategory() {
        return budgetViewCategory;
    }

}
