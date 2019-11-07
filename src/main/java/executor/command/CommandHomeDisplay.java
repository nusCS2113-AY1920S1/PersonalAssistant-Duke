package executor.command;

import storage.StorageManager;
import ui.UiCode;
import ui.gui.MainWindow;

public class CommandHomeDisplay extends Command {

    /**
     * Constructor for CommandHomeDisplay subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandHomeDisplay(String userInput) {
        this.userInput = userInput;
        this.description = "Displays the Home Page";
        this.commandType = CommandType.HOME;
    }

    @Override
    public void execute(StorageManager storageManager) {
        this.infoCapsule.setUiCode(UiCode.DISPLAY_HOME);
    }
}
