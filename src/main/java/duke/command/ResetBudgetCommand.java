package duke.command;

import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;
import duke.task.BudgetList;

import java.io.IOException;

public class ResetBudgetCommand extends Command {
    protected BudgetList budgetList;
    protected Ui ui = new Ui();
    protected float amount;


    /**
     * Obtaining the parameters of budget from budget corner.
     *
     * @param budgetList The list of budget that is stored by Duke Manager.
     * @param amount The amount to be updated.
     */
    public ResetBudgetCommand(BudgetList budgetList, float amount) {
        this.budgetList = budgetList;
        this.amount = amount;
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
        ui.showResetBudget(budgetList.getBudget());
        budgetList.resetBudget(amount);
        ui.showBudget(budgetList.getBudget());
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
        Float previousBudget = budgetList.getBudget();
        budgetList.resetBudget(amount);
        return ui.showResetBudgetGui(previousBudget) + "\n" + ui.showBudgetGui(budgetList.getBudget());
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
