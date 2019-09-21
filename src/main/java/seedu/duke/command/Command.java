package seedu.duke.command;

import seedu.duke.task.TaskList;

/**
 * Template for duke commands.
 */
public abstract class Command {
    
    /**
     * Execute the command.
     *
     * @param list the list of tasks on which the command is executed
     */
    public abstract void execute(TaskList list);
}
