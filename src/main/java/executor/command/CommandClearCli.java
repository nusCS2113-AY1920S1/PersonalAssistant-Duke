package executor.command;

import storage.StorageManager;
import ui.UiCode;

public class CommandClearCli extends Command {

    /**
     * Constructor for CommandClearCli class.
     */
    public CommandClearCli(String userInput) {
        super();
        this.userInput = userInput;
        this.commandType = CommandType.CLEAR;
        this.description = "Clears the current CLI window.";
    }

    @Override
    public void execute(StorageManager storageManager) {
        this.infoCapsule.setUiCode(UiCode.CLEAR_CLI);
        this.infoCapsule.setOutputStr("Command Clear Executed.");
    }
}
