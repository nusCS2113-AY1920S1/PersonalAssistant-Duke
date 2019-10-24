package executor.command;

import executor.task.TaskList;
import ui.Ui;
import ui.Wallet;

public class CommandBye extends Command {

    // Constructor
    /**
     * Constructor for CommandBye subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandBye(String userInput) {
        this.userInput = userInput;
        this.description = "Exits the program";
        this.commandType =  CommandType.BYE;
    }

    @Override
    public void execute(TaskList taskList) {
        this.exitRequest = true;
        Ui.dukeSays("Bye. Hope to see you again soon!");
    }

    @Override
    public void execute(Wallet wallet) {

    }
}
