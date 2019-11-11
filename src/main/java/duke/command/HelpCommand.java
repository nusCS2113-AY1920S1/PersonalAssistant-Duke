package duke.command;

import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

//@@author gervaiseang
/**
 * Displays HelpWindow that shows all functions and commands available.
 */
public class HelpCommand extends Command {

    /**
     * Executes a command that adds the task into task list and outputs the result (GUI).
     *
     * @param items The task list that contains a list of tasks.
     * @param ui To tell the user that it is added successfully.
     * @return String to be outputted to the user.
     */
    @Override
    public String executeGui(TaskList items, Ui ui) {
        return " ";
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