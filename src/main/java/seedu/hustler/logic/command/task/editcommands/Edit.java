package seedu.hustler.logic.command.task.editcommands;

import java.io.IOException;
import seedu.hustler.task.Task;

/**
 * Template for duke commands.
 */
public abstract class Edit {
    /**
     * Task to be edited.
     */
    protected Task task;

    /**
     * Initializes the task to be edited.
     *
     * @param task the task to be edited
     */
    public Edit(Task task) {
        this.task = task;
    }

    /**
     * Executes the command.
     */
    public abstract void execute() throws IOException;
}
