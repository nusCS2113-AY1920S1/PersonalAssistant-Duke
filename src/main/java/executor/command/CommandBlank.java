package executor.command;

import executor.task.TaskList;
import ui.Ui;
import ui.Wallet;

public class CommandBlank extends Command {
    // Constructor
    public CommandBlank() {
    }

    /**
     * Executes a particular Command.
     */
    @Override
    public void execute(TaskList taskList) {
        Ui.printSeparator();
    }

    @Override
    public void execute(Wallet wallet) {

    }
}
