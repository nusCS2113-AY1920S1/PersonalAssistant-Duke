package duke.model;

import duke.commons.LogsCenter;
import duke.exception.DukeException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public class Budget {


    private static final Logger logger = LogsCenter.getLogger(Budget.class);


    private BigDecimal monthlyBudget;
    /**
     * Maps a category to the budget set for the category.
     */
    private Map<String, BigDecimal> budgetCategory;

    private ObservableList<String> budgetObservableList;

    public Budget(BigDecimal monthlyBudget, Map<String, BigDecimal> budgetCategory) {
        this.monthlyBudget = monthlyBudget;
        this.budgetCategory = budgetCategory;
        budgetObservableList = FXCollections.observableArrayList();
        updateBudgetObservableList();
    }

    /**
     * Setter method for monthlyBudget.
     *
     * @param monthlyBudget BigDecimal budget set for each month
     */
    public void setMonthlyBudget(BigDecimal monthlyBudget) {
        this.monthlyBudget = monthlyBudget;
        updateBudgetObservableList();
    }

    /**
     * Gets a string value for monthlyBudget.
     *
     * @return a String of the monthly budget
     */
    public String getMonthlyBudgetString() {
        return monthlyBudget.toPlainString();
    }

    /**
     * Sets budget to a given category.
     *
     * @param category the String tag specified that we want to set a budget for
     * @param budget   a BigDecimal amount for the budget we want to set
     */
    public void setCategoryBudget(String category, BigDecimal budget) {
        budgetCategory.put(category, budget);
        updateBudgetObservableList();
    }

    /**
     * Gets the difference between the monthly budget and the total expenses spent.
     *
     * @param total the BigDecimal total expenditure from expenseList
     * @return BigDecimal value fo the difference
     */
    public BigDecimal getRemaining(BigDecimal total) {
        return monthlyBudget.subtract(total);
    }

    public Map<String, BigDecimal> getBudgetCategory() {
        return budgetCategory;
    }

    public ObservableList<String> getBudgetObservableList() {
        return budgetObservableList;
    }

    private void updateBudgetObservableList() {
        budgetObservableList.clear();
        budgetObservableList.add("Overall monthly budget: $" + monthlyBudget.toString());
        for(String category : budgetCategory.keySet()){
            budgetObservableList.add(category + ": " + budgetCategory.get(category));
        }
        logger.info("Size of budgetObserverList: $" + budgetObservableList.size() );
    }

}
