package seedu.hustler.logic.command.task.editcommands;

import java.io.IOException;
import seedu.hustler.task.Task;
import seedu.hustler.task.TaskList;

/**
 * Template for duke commands.
 */
public abstract class Edit {

    /**
     * Index of task in Hustler's TaskList.
     */
    protected int index;
    
    /**
     * Initializes index.
     *
     * @param index index to be initialized with
     */
    public Edit(int index) {
        this.index = index;
    }

    /**
     * Executes the command.
     */
    public abstract void execute();
}
