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


    /**
     * Executes a command with task list and ui.
     * (not used)
     *
     * @param items The task list that contains a list of tasks.
     * @param ui    To tell the user that it is executed successfully.
     */
    @Override
    public void execute(TaskList items, Ui ui) {

    }

    /**
     * Executes a command with task list and ui (GUI).
     * (not used)
     *
     * @param items The task list that contains a list of tasks.
     * @param ui    To tell the user that it is executed successfully.
     * @return String to be outputted to the user.
     */
    @Override
    public String executeGui(TaskList items, Ui ui) {
        return null;
    }

    /**
     * Executes a command that overwrites existing storage with the updated task list.
     * (not used)
     *
     * @param items   The task list that contains a list of tasks.
     * @param ui      To tell the user that it is executed successfully.
     * @param storage The storage to be overwritten.
     * @throws IOException If there is an error reading the file.
     */
    @Override
    public void executeStorage(TaskList items, Ui ui, Storage storage) throws IOException {

    }
}
