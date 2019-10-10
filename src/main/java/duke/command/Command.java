package duke.command;

import duke.task.TaskList;

import java.text.ParseException;

public abstract class Command {
    protected Boolean exitRequest = false;
    protected CommandType commandType;

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
