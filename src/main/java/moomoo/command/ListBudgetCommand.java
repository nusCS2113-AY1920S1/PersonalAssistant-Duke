package moomoo.command;

import moomoo.task.Budget;
import moomoo.task.Category;
import moomoo.task.CategoryList;
import moomoo.task.ScheduleList;
import moomoo.task.Storage;
import moomoo.task.Ui;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Lists the budget for respective categories.
 */
public class ListBudgetCommand extends Command {
    private ArrayList<String> categories;
    private DecimalFormat df;

    /**
     * Initializes the command to list the set budgets.
     * @param isExit Boolean variable to determine if program should exit.
     * @param categories List of categories to list the budgets for.
     */
    public ListBudgetCommand(boolean isExit, ArrayList<String> categories) {
        super(isExit, "");
        this.categories = categories;
        df = new DecimalFormat("#.00");
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList catList, Category category,
                        Ui ui, Storage storage) {
        String outputValue = "";
        if (categories.size() == 0) {
            for (int i = 0; i < catList.getCategoryList().size(); ++i) {
                String categoryName = catList.getCategoryList().get(i).toString();
                outputValue += "Budget for " + categoryName + " is $"
                        + df.format(budget.getBudgetFromCategory(categoryName)) + "\n";
            }
            ui.setOutput(outputValue);
            return;
        }

        for (int i = 0; i < categories.size(); ++i) {
            String categoryName = categories.get(i);

            if (inCategoryList(categoryName, catList) != null) {
                if (budget.getBudgetFromCategory(categoryName) == 0) {
                    outputValue += "Budget for " + categoryName + " has not been set.\n";
                    continue;
                }
                outputValue += "Budget for " + categoryName + " is $"
                        + df.format(budget.getBudgetFromCategory(categoryName)) + "\n";
            } else {
                outputValue += categoryName + " category does not exist. Please add it first.\n";
            }
        }
        ui.setOutput(outputValue);
    }

    private Category inCategoryList(String value, CategoryList catList) {
        for (Category iterCategory : catList.getCategoryList()) {
            if (iterCategory.toString().toLowerCase().equals(value.toLowerCase())) {
                return iterCategory;
            }
        }
        return null;
    }
}
