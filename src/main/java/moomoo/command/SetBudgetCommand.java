package moomoo.command;

import moomoo.feature.Budget;
import moomoo.feature.category.CategoryList;
import moomoo.feature.MooMooException;
import moomoo.feature.ScheduleList;
import moomoo.feature.storage.Storage;
import moomoo.feature.Ui;

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
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryListList,
                        Ui ui, Storage storage) throws MooMooException {
        String outputValue = "";
        boolean isUpdated = false;

        for (int i = 0; i < categories.size(); ++i) {
            String categoryName = categories.get(i).toLowerCase();
            double categoryBudget = budgets.get(i);
            if (categoryListList.get(categoryName) != null) {
                if (categoryBudget <= 0) {
                    outputValue += "Please set your budget for " + categoryName + " to a value more than 0\n";
                    continue;
                }
                if (budget.getBudgetFromCategory(categoryName) != 0) {
                    outputValue += "Please edit your budget for " + categoryName + " using budget edit.\n";
                    continue;
                }

                isUpdated = true;
                budget.addNewBudget(categoryName, categoryBudget);
                int blank = 22 - categoryName.length();
                String blankSpace = " ";
                for (int j = 0; j < blank; j++) {
                    blankSpace += " ";
                }
                blank = 32 - String.valueOf(df.format(categoryBudget)).length();
                String blank2 = " ";
                for (int j = 0; j < blank; j++) {
                    blank2 += " ";
                }
                String cow =
                      ".__________________________________.\n"
                       + "| ___ _   _ ___   ___ ___ _____    |\n"
                       + "|| _ ) | | |   \\ / __| _ |_   _|   |\n"
                       + "|| _ \\ |_| | |) | (_ | _|  | |     |\n"
                       + "||___/\\___/|___/ \\___|___| |_|     |\n"
                       + "|                                  |\n"
                       + "|Category : " + categoryName + blankSpace + "|\n"
                       + "|$" + df.format(categoryBudget) + blank2 + "|\n"
                       + ".----------------------------------.\n"
                       + "        \\   ^__^\n"
                       + "         \\  (oo)\\_______\n"
                       + "            (__)\\       )\\/\\\n"
                       + "                ||----w |\n"
                       + "                ||     ||\n";
                outputValue += cow;
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
