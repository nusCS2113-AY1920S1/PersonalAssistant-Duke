package executor.command;

import executor.task.TaskList;
import ui.Ui;
import ui.Wallet;

public class CommandError extends Command {
    // Constructor
    public CommandError(String userInpt) {
        this.userInput = userInput;
    }

    /**
     * Executes a particular Command.
     */
    @Override
    public void execute(TaskList taskList) {
        Ui.dukeSays("Duke has encountered an error");
        Ui.printSeparator();
    }

    @Override
    public void execute(Wallet wallet) {

    }
}
