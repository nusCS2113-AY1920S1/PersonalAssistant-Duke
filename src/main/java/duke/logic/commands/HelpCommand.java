package duke.logic.commands;

import duke.models.LockerList;
import duke.storage.Storage;
import duke.ui.Ui;

/**
 * Command to display the help manual.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";

    @Override
    public void execute(LockerList lockerList, Ui ui, Storage storage) {
        ui.printHelp();
    }
}
