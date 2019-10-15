package duke.command;

import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;
import duke.task.BudgetList;

import java.io.IOException;

public class UpdateBudgetCommand extends Command {
    protected BudgetList budgetList;
    protected Ui ui = new Ui();

    /**
     * Updates the current budget with the input amount, and prompts the user to confirm
     * his/her actions before carrying on with the action.
     *
     * @param budgetList The list of budget that is stored by Duke Manager.
     * @param amount The amount to be updated.
     */
    public UpdateBudgetCommand(BudgetList budgetList, float amount) {
        if (ui.isBudgetResetTrue()) {
            budgetList.resetBudget(amount);
            ui.showBudget(budgetList.getBudget());
        } else {
            ui.rejectBudgetResetMessage();
        }
    }

    @Override
    public void execute(TaskList items, Ui ui) {

    }

    @Override
    public String executeGui(TaskList items, Ui ui) {
        return null;
    }

    @Override
    public void executeStorage(TaskList items, Ui ui, Storage storage) throws IOException {

    }
}
