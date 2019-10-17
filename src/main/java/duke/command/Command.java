package duke.command;

import duke.exception.DukeException;
import duke.recipebook.dishlist;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * Represents  an abstract Command that could be an add, delete, exit, done, find or list.
 */
public abstract class Command {

    public abstract void execute(dishlist dish1, TaskList taskList, Ui ui, Storage storage) throws DukeException;

    /**
     * Returns the boolean indicating that it is( not) an {@link ExitCommand}.
     *
     * @return false by default
     */
    public boolean isExit() {
        return false;
    }
}
