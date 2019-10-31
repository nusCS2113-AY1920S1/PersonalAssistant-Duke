package executor.command;

import executor.task.TaskList;
import ui.Ui;
import ui.Wallet;

public class CommandError extends Command {
    // Constructor
    /**
     * Constructor for CommandError subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandError(String userInput) {
        this.userInput = userInput;
        this.description = "Prints error message when program encounters an error";
        this.commandType = CommandType.ERROR;
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
