package javacake.commands;

import javacake.Logic;
import javacake.exceptions.CakeException;
import javacake.storage.StorageManager;
import javacake.ui.Ui;

public class ResetCommand extends Command {

    /**
     * Constructor for ResetCommand.
     * Checks that no parameters are included.
     * @param inputCommand Reset command from user.
     * @throws CakeException If other parameter is appended to command.
     */
    public ResetCommand(String inputCommand) throws CakeException {
        checksParam(inputCommand);
    }

    @Override
    public String execute(Logic logic, Ui ui, StorageManager storageManager) throws CakeException {
        return "Confirm reset and deletion of current Profile?\nType 'yes' to confirm\n"
                + "Type anything else to cancel\n";
    }
}
