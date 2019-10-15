package duke.command;

import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;
import duke.task.BudgetList;

import duke.dukeexception.DukeException;
import java.io.IOException;

public class AddBudgetCommand extends Command {

    protected Ui ui = new Ui();

    /**
     * Adds the amount specified into the budgetList.
     *
     * @param budgetList The list of budget that is stored by Duke Manager.
     * @param amount amount to be updated in the user's budget.
     */
    public AddBudgetCommand(BudgetList budgetList, float amount) {
        budgetList.addToBudget(amount);
        ui.showBudget(budgetList.getBudget());
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
