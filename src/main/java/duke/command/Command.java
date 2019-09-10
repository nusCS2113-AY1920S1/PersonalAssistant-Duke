package duke.command;

import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Abstract class representing individual commands.
 */
public abstract class Command {
    boolean exit = false;

    /**
     * Determines whether this command is exiting, i.e. whether the program should exit
     * after executing this command.
     *
     * @return Whether this command is exiting.
     */
    public boolean isExit() {
        return exit;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param tasks The task list.
     * @param ui The user interface displaying events on the task list.
     * @param storage The place where tasks will be stored.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage);
}
