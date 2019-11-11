package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.log.Log;
import duke.models.LockerList;
import duke.models.locker.Locker;
import duke.models.locker.SerialNumber;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

/**
 * Command to delete a locker from the list of lockers in SpongeBob.
 */
public class DeleteLockerCommand extends Command {

    private static final String LOG_FOR_DELETE_LOCKER = " Executing command for deleting a locker";
    private final SerialNumber serialNumberToDelete;
    public static final String COMMAND_WORD = "deletelocker";
    public static final String INVALID_FORMAT = " Invalid command format for deleting lockers."
            + "\n     1. Should enter only deleteLocker <SERIALNUMBER> ";
    private static final Logger logger = Log.getLogger();

    /**
     * Creates a DeleteLockerCommand to delete the locker associated with the {@code SerialNumber} .
     */
    public DeleteLockerCommand(SerialNumber serialNumber) {
        requireNonNull(serialNumber);
        this.serialNumberToDelete = serialNumber;
    }

    @Override
    public void execute(LockerList lockerList, Ui ui, Storage storage) throws DukeException {
        logger.log(Level.INFO, LOG_FOR_DELETE_LOCKER);
        Locker lockerToDelete = lockerList.getLockerToEdit(serialNumberToDelete);
        lockerList.deleteLocker(lockerToDelete);
        ui.deleteMessage(lockerList.numLockers(), lockerToDelete.toString());
        storage.saveData(lockerList);
        storage.updateStateList(lockerList);
    }
}
