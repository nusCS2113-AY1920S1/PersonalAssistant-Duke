package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.models.locker.Locker;
import duke.models.locker.SerialNumber;
import duke.storage.Storage;
import duke.ui.Ui;

import static java.util.Objects.requireNonNull;

/**
 * Command to delete a locker from the list of lockers in SpongeBob.
 */
public class DeleteLockerCommand extends Command {

    private final SerialNumber serialNumberToDelete;
    public static final String COMMAND_WORD = "deletelocker";
    public static final String INVALID_FORMAT = " Invalid command format for deleting lockers."
            + "\n     1. Should enter only deleteLocker <SERIALNUMBER> ";

    /**
     * Creates a DeleteLockerCommand to delete the locker associated with the {@code SerialNumber} .
     */
    public DeleteLockerCommand(SerialNumber serialNumber) {
        requireNonNull(serialNumber);
        this.serialNumberToDelete = serialNumber;
    }

    @Override
    public void execute(LockerList lockerList, Ui ui, Storage storage) throws DukeException {

        Locker lockerToDelete = lockerList.getLockerToEdit(serialNumberToDelete);
        lockerList.deleteLocker(lockerToDelete);
        ui.deleteMessage(lockerList.numLockers(), lockerToDelete.toString());
        storage.saveData(lockerList);
    }
}
