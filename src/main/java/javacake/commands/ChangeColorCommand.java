package javacake.commands;

import javacake.Logic;
import javacake.exceptions.CakeException;
import javacake.storage.StorageManager;
import javacake.ui.Ui;

public class ChangeColorCommand extends Command {

    /**
     * Constructor for ChangeColorCommand.
     * Checks that no parameters are included.
     * @param inputCommand input from user.
     * @throws CakeException If other parameter is appended to command.
     */
    public ChangeColorCommand(String inputCommand) throws CakeException {
        checksParam(inputCommand);
    }

    @Override
    public String execute(Logic logic, Ui ui, StorageManager storageManager) {
        return "Changed color mode!\nType 'list' for more commands\nType 'help' for command info\n";
    }
}
