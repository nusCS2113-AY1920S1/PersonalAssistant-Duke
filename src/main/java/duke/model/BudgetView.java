package duke.model;

import duke.commons.LogsCenter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.util.Map;
import java.util.logging.Logger;

public class BudgetView {


    private static final Logger logger = LogsCenter.getLogger(BudgetView.class);


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
