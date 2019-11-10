package executor.command;

import storage.StorageManager;
import ui.UiCode;

public class CommandEnableTesting extends Command {
    /**
     * Constructor for CommandEnableTesting Class.
     */
    public CommandEnableTesting(String userInput) {
        super();
        this.userInput = userInput;
        this.commandType = CommandType.ENABLETEST;
        this.description = "TESTERS: Enables Testing Mode by loading test data.";
    }

    @Override
    public void execute(StorageManager storageManager) {
        this.infoCapsule.setUiCode(UiCode.TESTER);
        this.infoCapsule.setOutputStr("Enabling testing mode...");
    }
}
