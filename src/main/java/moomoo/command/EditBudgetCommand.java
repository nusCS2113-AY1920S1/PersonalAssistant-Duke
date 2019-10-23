package moomoo.command;

import moomoo.task.Budget;
import moomoo.task.Category;
import moomoo.task.CategoryList;
import moomoo.task.MooMooException;
import moomoo.task.ScheduleList;
import moomoo.task.Storage;
import moomoo.task.Ui;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Edits the budgets for respective categories.
 */
public class EditBudgetCommand extends Command {
    private ArrayList<String> categories;
    private ArrayList<Double> budgets;
    private DecimalFormat df;

    /**
     * Initializes the Edit budget command.
     * @param isExit Boolean variable to determine if program should exit.
     * @param categories List of categories to edit.
     * @param budgets List of budgets to change corresponding categories to.
     */
    public EditBudgetCommand(boolean isExit, ArrayList<String> categories, ArrayList<Double> budgets) {
        super(isExit, "");
        this.categories = categories;
        this.budgets = budgets;
        df = new DecimalFormat("#.00");

    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList catList, Category category,
                        Ui ui, Storage storage) throws MooMooException {
        String outputValue = "";
        boolean isUpdated = false;

        for (int i = 0; i < categories.size(); ++i) {
            String categoryName = categories.get(i).toLowerCase();
            double categoryBudget = budgets.get(i);

            if (catList.returnCategory(categoryName) != null) {
                double currentBudget = budget.getBudgetFromCategory(categoryName);
                if (currentBudget == 0) {
                    outputValue += "Budget for " + categoryName + " has not been set. Please set it first.\n";
                    continue;
                }
                if (currentBudget == categoryBudget) {
                    outputValue += "The budget for " + categoryName + " is the same.\n";
                    continue;
                }
                isUpdated = true;
                budget.addNewBudget(categoryName, categoryBudget);
                outputValue += "You have changed the budget for " + categoryName
                        + " from $" + df.format(currentBudget) + " to $" + df.format(categoryBudget) + "\n";
            } else {
                outputValue += categoryName + " category does not exist. Please add it first.\n";
            }
        }
        ui.setOutput(outputValue);
        if (isUpdated) {
            storage.saveBudgetToFile(budget);
        }
    }
}
