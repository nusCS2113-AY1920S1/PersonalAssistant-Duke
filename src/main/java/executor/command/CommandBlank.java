package executor.command;

import executor.task.TaskList;
import ui.Ui;
import ui.Wallet;

public class CommandBlank extends Command {

    // Constructor
    /**
     * Constructor for CommandBlank subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandBlank(String userInput) {
        this.userInput = userInput;
        this.description = "You can print a line separator";
        this.commandType = CommandType.BLANK;
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
