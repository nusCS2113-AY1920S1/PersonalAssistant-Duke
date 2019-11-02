package javacake.commands;

import javacake.exceptions.CakeException;
import javacake.Logic;
import javacake.storage.StorageManager;
import javacake.ui.Ui;

public class ListCommand extends Command {

    /**
     * Constructor for ListCommand.
     * Checks that no parameters are included.
     * @param inputCommand List command from user.
     * @throws CakeException If other parameter is appended to command.
     */
    public ListCommand(String inputCommand) throws CakeException {
        checksParam(inputCommand);
        type = CmdType.LIST;
    }

    /**
     * Execute the listing of current tasks on the Ui.
     * @param logic TaskList containing current tasks
     * @param ui the Ui responsible for outputting messages
     * @param storageManager storage container
     */
    @Override
    public String execute(Logic logic, Ui ui, StorageManager storageManager) throws CakeException {
        logic.setDefaultFilePath();
        return (logic.processQueries());
    }
}
