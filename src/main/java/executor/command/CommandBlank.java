package executor.command;

import storage.StorageManager;

public class CommandBlank extends Command {

    /**
     * Constructor for CommandBlank subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandBlank(String userInput) {
        super();
        this.userInput = userInput;
        this.description = "Does Nothing";
        this.commandType = CommandType.BLANK;
    }

    /**
     * Executes a particular Command.
     */
    @Override
    public void execute(StorageManager storageManager) {
        this.infoCapsule.setCodeToast();
        this.infoCapsule.setOutputStr("");
    }
}
