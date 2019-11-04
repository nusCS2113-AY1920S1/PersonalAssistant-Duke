package moomoo.command;

import moomoo.task.Budget;
import moomoo.task.category.Category;
import moomoo.task.category.CategoryList;
import moomoo.task.MooMooException;
import moomoo.task.ScheduleList;
import moomoo.task.Storage;
import moomoo.task.Ui;

public class NotificationCommand extends Command {
    private String cat;
    private double expenditure;

    /**
     * Alerts user if user exceeded the budget.
     *
     * @param cat         The name of the category that user just added his expenditure to.
     * @param expenditure The total current expenditure of that category.
     */
    public NotificationCommand(String cat, double expenditure) {
        super(false, "");
        this.cat = cat;
        this.expenditure = expenditure;
    }

    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList catList, Category category,
                        Ui ui, Storage storage) throws MooMooException {
        String alert = "";
        if (expenditure > budget.getBudgetFromCategory(cat)) {
            alert = "\u001B[36m" + "You have exceeded your budget of " + budget.getBudgetFromCategory(cat)
                    + " for " + cat + "!\n";
        } else if (expenditure == budget.getBudgetFromCategory(cat)) {
            alert = "\u001B[36m" + "You have reached your budget limit of " + budget.getBudgetFromCategory(cat)
                    + ".\n";
        } else if (expenditure > budget.getBudgetFromCategory(cat) * 0.9) {
            alert = "\u001B[36m" + "You are reaching your budget limit of " + budget.getBudgetFromCategory(cat)
                    + " for " + cat
                    + ".\n";
        }
        double balance = budget.getBudgetFromCategory(cat) - expenditure;
        ui.setOutput(alert + "Budget remaining = " + balance);
        ui.showResponse();
    }
}

