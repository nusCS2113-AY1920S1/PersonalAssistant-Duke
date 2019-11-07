package executor.command;

import storage.StorageManager;

public class CommandBye extends Command {

    /**
     * Constructor for CommandBye subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandBye(String userInput) {
        super();
        this.userInput = userInput;
        this.description = "Exits the program \n"
                + "FORMAT : bye";
        this.commandType =  CommandType.BYE;
    }

    public void execute(StorageManager storageManager) {
        this.infoCapsule.setCodeExit();
    }
}
