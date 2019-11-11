package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.storage.Storage;
import duke.ui.Ui;

/**
 * Command to clear all the lockers from the list of lockers.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";

    @Override
    public void execute(LockerList lockerList, Ui ui, Storage storage) throws DukeException {
        lockerList.removeAllLockers();
        ui.showAllLockersRemoved();
        storage.saveData(lockerList);
    }
}
