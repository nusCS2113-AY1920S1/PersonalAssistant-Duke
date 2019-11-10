package rims.command;

import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;

//@@author rabhijit
/**
 * Prints a list of all supported commands, and a description of those commands,
 * for the user.
 */
public class HelpCommand extends Command {
    /**
     * Prints the list of commands that is stored in the Ui instance.
     * @param ui An instance of the user interface.
     * @param storage An instance of the Storage class.
     * @param resources The ResourceList, containing all the created Resources thus far.
     */
    @Override
    public void execute(Ui ui, Storage storage, ResourceList resources) {
        ui.help();
    }
}