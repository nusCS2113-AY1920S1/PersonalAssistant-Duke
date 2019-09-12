package duke.command;

import duke.task.TaskList;

public abstract class Command {
    protected Boolean exitRequest = false;

    // Constructor
    public Command() {
    }

    /**
     * Returns True if the command requests for the Ui to exit.
     *
     * @return Boolean
     */
    public Boolean getExitRequest() {
        return exitRequest;
    }

    /**
     * Executes a particular Command.
     */
    public abstract void execute(TaskList taskList);
}
