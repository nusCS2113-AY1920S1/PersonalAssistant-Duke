package duke.command;

import duke.core.DukeExceptionThrow;
import duke.core.Storage;
import duke.core.TaskList;
import duke.core.Ui;

/**
 * Represents a command to find a certain task from Duke's task list.
 * The ExitCommand class extends from the Command
 * class for the user to find a specific task object from the storage.
 */
public class FindCommand extends Command {
    /**
     * Name of the task to be found.
     */
    private String taskName;
    /**
     * Constructs a FindCommand object.
     * @param name Specifies the name of the task.
     */
    public FindCommand(String name) {
        super();
        this.taskName = name;
    }
    /**
     * Indicates whether Duke should exist
     * @return A boolean. True if the command tells duke.Duke to exit, false
     *          otherwise.
     */
    @Override
    public boolean isExit() {
        return false;
    }
    /**
     * run the command with the respect duke.core.TaskList, UI, and storage.
     * @param tasks The task list where tasks are saved.
     * @param ui The user interface.
     * @param storage object that handles local text file update
     */
    @Override
    public void run(TaskList tasks, Ui ui, Storage storage) throws DukeExceptionThrow {
        ui.taskFound(tasks.fullTaskList(), taskName);
    }
}