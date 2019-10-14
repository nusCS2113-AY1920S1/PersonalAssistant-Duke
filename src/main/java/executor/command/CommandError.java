package executor.command;

import executor.task.TaskList;
import ui.Ui;

public class CommandError extends Command {
    // Constructor
    public CommandError() {
    }

    /**
     * Executes a particular Command.
     */
    @Override
    public void execute(TaskList taskList) {
        Ui.dukeSays("Duke has encountered an error");
        Ui.printSeparator();
    }
}
