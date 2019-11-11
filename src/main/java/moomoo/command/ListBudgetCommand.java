package moomoo.command;

import moomoo.feature.Budget;
import moomoo.feature.ScheduleList;
import moomoo.feature.Ui;
import moomoo.feature.storage.Storage;
import moomoo.feature.MooMooException;
import moomoo.feature.category.CategoryList;

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
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList,
                        Storage storage) throws MooMooException {
        ArrayList<String> outputArray = new ArrayList<>();
        String outputValue = "";
        double currentBudget = 0;
        if (categories.size() == 0) {
            for (int i = 0; i < categoryList.getCategoryList().size(); ++i) {
                String categoryName = categoryList.getCategoryList().get(i).name().toLowerCase();
                currentBudget = budget.getBudgetFromCategory(categoryName);
                if (currentBudget == 0) {
                    outputValue = "Budget for " + categoryName + " has not been set";
                    outputArray.add(outputValue);
                    continue;
                }
                outputValue = "Budget for " + categoryName + " is $"
                        + df.format(currentBudget);
                outputArray.add(outputValue);
            }
            if (outputValue.equals("")) {
                throw new MooMooException("You have yet to set a budget for any category.");
            }
            outputValue = Ui.showInCowBox(outputArray);
            Ui.setOutput(outputValue);
            return;
        }


        for (int i = 0; i < categories.size(); ++i) {
            String categoryName = categories.get(i).toLowerCase();

            if (categoryList.get(categoryName) != null) {
                currentBudget = budget.getBudgetFromCategory(categoryName);
                if (currentBudget == 0) {
                    outputValue = "Budget for " + categoryName + " has not been set.";
                    outputArray.add(outputValue);
                    outputValue = Ui.showInCowBox(outputArray);
                    continue;
                }
                outputValue = "Budget for " + categoryName + " is $"
                        + df.format(currentBudget);
                outputArray.add(outputValue);
                outputValue = Ui.showInCowBox(outputArray);
            } else {
                outputValue = categoryName + " category does not exist. Please add it first.\n";
            }
        }
        Ui.setOutput(outputValue);
    }
}
