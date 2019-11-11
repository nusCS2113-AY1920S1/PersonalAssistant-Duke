package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.log.Log;
import duke.models.LockerList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Command to clear all the lockers from the list of lockers.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    private static final Logger logger = Log.getLogger();
    private static final String LOG_FOR_CLEAR_COMMAND = " Executing command for clearing the list of lockers";

    @Override
    public void execute(LockerList lockerList, Ui ui, Storage storage) throws DukeException {
        logger.log(Level.INFO, LOG_FOR_CLEAR_COMMAND);
        lockerList.removeAllLockers();
        ui.showAllLockersRemoved();
        storage.saveData(lockerList);
        storage.updateStateList(lockerList);
    }
}
