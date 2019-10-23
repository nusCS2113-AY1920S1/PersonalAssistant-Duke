package duke.command;

import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

//@@author talesrune
/**
 * Representing a command that locates certain tasks in task list using keyword.
 */
public class FindCommand extends Command {
    protected String keyword;

    /**
     * Creates a command with the specified keyword.
     *
     * @param keyword The task to be added.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Executes a command that locates matching tasks in task list and outputs the result.
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user the matching tasks based on the keyword.
     */
    @Override
    public void execute(TaskList items, Ui ui) {
        ui.showFind(items, keyword);
    }

    /**
     * Executes a command that locates matching tasks in task list and outputs the result (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user the matching tasks based on the keyword.
     * @return List of tasks.
     */
    @Override
    public String executeGui(TaskList items, Ui ui) {
        String str = Ui.showFindGui(items, keyword);
        return str;
    }

    /**
     * Executes a command that overwrites existing storage with the updated task list.
     * (Not in use)
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is executed successfully.
     * @param storage The storage to be overwritten.
     */
    @Override
    public void executeStorage(TaskList items, Ui ui, Storage storage) {

    }
}