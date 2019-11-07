package executor.command;

import storage.StorageManager;
import ui.UiCode;

public class CommandCliDisplay extends Command {

    /**
     * Constructor for CommandCliDisplay subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandCliDisplay(String userInput) {
        this.userInput = userInput;
        this.description = "Displays the Command Line Display";
        this.commandType = CommandType.CLI;
    }

    @Override
    public void execute(StorageManager storageManager) {
        this.infoCapsule.setUiCode(UiCode.DISPLAY_CLI);
    }
}
