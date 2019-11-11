package duke.logic.commands;


import duke.log.Log;
import duke.models.LockerList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command to list all the lockers.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    private static final Logger logger = Log.getLogger();
    private static final String LOG_FOR_LIST_COMMAND = "Executing list command";

    @Override
    public void execute(LockerList lockerList, Ui ui, Storage storage) {
        logger.log(Level.INFO, LOG_FOR_LIST_COMMAND);
        ui.printList(lockerList.getLockerList());
    }
}
