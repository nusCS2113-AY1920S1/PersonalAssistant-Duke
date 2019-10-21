package duke.command;

import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;
import duke.task.BudgetList;

import duke.dukeexception.DukeException;
import java.io.IOException;

//@@author maxxyx96
public class ViewBudgetCommand extends Command {

    protected Ui ui = new Ui();
    protected BudgetList budgetList;

    /**
     * Command that allows the user to view the budget that he/she currently has.
     *
     * @param budgetList The list of budget that is stored by Duke Manager.
     */
    public ViewBudgetCommand(BudgetList budgetList) {
        this.budgetList = budgetList;
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
        return ui.showBudgetGui(budgetList.getBudget());
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
//@@author