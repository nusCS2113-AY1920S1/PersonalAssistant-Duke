package executor.command;

import executor.task.TaskList;
import ui.Wallet;

public abstract class Command {
    protected Boolean exitRequest = false;
    protected CommandType commandType;
    protected String description = "Basic Command";

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

    public abstract void execute(Wallet wallet);

    public String getDescription() {
        return this.description;
    }
}
