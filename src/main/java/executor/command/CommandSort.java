package executor.command;

import executor.task.TaskList;
import ui.Wallet;

public class CommandSort extends Command {

    // Constructor
    /**
     * Constructor for CommandSort subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandSort(String userInput) {
        this.userInput = userInput;
        this.commandType = CommandType.SORT;

    }

    @Override
    public void execute(TaskList taskList) {

    }

    @Override
    public void execute(Wallet wallet) {

    }
}
