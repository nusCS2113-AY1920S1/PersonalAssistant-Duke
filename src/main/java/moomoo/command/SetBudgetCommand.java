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
 * Sets the budget for respective categories.
 */
public class SetBudgetCommand extends Command {
    private ArrayList<String> categories;
    private ArrayList<Double> budgets;
    private DecimalFormat df;

    /**
     * Initializes the set budget command.
     * @param isExit Boolean variable to determine if program should exit.
     * @param categories List of categories to set the budget for.
     * @param budgets List of budgets to set the corresponding categories to.
     */
    public SetBudgetCommand(boolean isExit, ArrayList<String> categories, ArrayList<Double> budgets) {
        super(isExit, "");
        this.categories = categories;
        this.budgets = budgets;
        df = new DecimalFormat("#.00");
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList catList, Category category,
                        Ui ui, Storage storage) throws MooMooException {
        String outputValue = "";
        for (int i = 0; i < categories.size(); ++i) {
            String categoryName = categories.get(i).toLowerCase();
            double categoryBudget = budgets.get(i);

            if (inCategoryList(categoryName, catList) != null) {
                budget.addNewBudget(categoryName, categoryBudget);
                outputValue += "You have set $" + df.format(categoryBudget) + " as the budget for "
                        + categoryName + "\n";
            } else {
                outputValue += categoryName + " category does not exist. Please add it first.\n";
            }
        }
        ui.setOutput(outputValue);
        storage.saveBudgetToFile(budget);

    }

    private Category inCategoryList(String value, CategoryList catList) {
        for (Category iterCategory : catList.getCategoryList()) {
            if (iterCategory.toString().equalsIgnoreCase(value)) {
                return iterCategory;
            }
        }
        return null;
    }
}
