package executor.command;

import storage.StorageManager;

public class CommandError extends Command {
    /**
     * Constructor for CommandError subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandError(String userInput) {
        super();
        this.userInput = userInput;
        this.description = "Prints error message when program encounters an error";
        this.commandType = CommandType.ERROR;
    }

    /**
     * Executes a particular Command.
     */
    @Override
    public void execute(StorageManager storageManager) {
        this.infoCapsule.setCodeError();
        this.infoCapsule.setOutputStr("Please enter a valid Command. Type 'help' to see the list of Commands.\n");
    }
}
