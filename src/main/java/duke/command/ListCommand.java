package duke.command;

import duke.core.DukeException;
import duke.core.Storage;
import duke.core.TaskList;
import duke.core.Ui;

/**
 * Represents a command to list all existing tasks in the task list. The
 * ListCommand class extends from the command.Command class
 * for the user to view the entire task list from Duke.
 */
public class ListCommand extends Command {
    /**
     * Constructs a command.ListCommand object.
     */
    public ListCommand() {
        super();
    }

    /**
     * Indicates whether Duke should exist
     *
     * @return A boolean. True if the command tells Duke to exit, false
     * otherwise.
     */
    @Override
    public boolean isExit() {
        return false;
    }

    /**
     * run the command with the respect TaskList, UI, and storage.
     *
     * @param tasks   The task list where tasks are saved.
     * @param ui      The user interface.
     * @param storage object that handles local text file update
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        ui.listTasks(tasks);
    }
}