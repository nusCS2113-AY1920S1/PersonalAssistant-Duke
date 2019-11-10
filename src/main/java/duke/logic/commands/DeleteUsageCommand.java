package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.models.locker.Locker;
import duke.models.locker.SerialNumber;
import duke.models.tag.Tag;
import duke.storage.Storage;
import duke.ui.Ui;

import static java.util.Objects.requireNonNull;

/**
 * Command to delete the usage/subscription of an in-use locker.
 */
public class DeleteUsageCommand extends Command {

    private final SerialNumber serialNumberToDeleteUsage;
    public static final String COMMAND_WORD = "deleteusage";
    public static final String INVALID_FORMAT = " Invalid command format for deleting usage. "
            + "You must key in the serial number of the locker";
    private static final String USAGE_CONSTRAINT = " Usage of only an in-use locker can be deleted.";

    /**
     * Creates a DeleteUsageCommand to delete the subscription details of the locker associated
     * with the {@code SerialNumber} .
     */
    public DeleteUsageCommand(SerialNumber serialNumber) {
        requireNonNull(serialNumber);
        this.serialNumberToDeleteUsage = serialNumber;
    }

    @Override
    public void execute(LockerList lockerList, Ui ui, Storage storage) throws DukeException {
        Locker lockerToDelete = lockerList.getLockerToEdit(serialNumberToDeleteUsage);
        if (!(lockerToDelete.isOfTypeInUse())) {
            throw new DukeException(USAGE_CONSTRAINT);
        }
        lockerList.setLockerInPosition(new Locker(lockerToDelete.getSerialNumber(),
                lockerToDelete.getAddress(), lockerToDelete.getZone(), new Tag(Tag.NOT_IN_USE),null),
                lockerList.getIndexOfLocker(lockerToDelete));
        ui.showDeleteUsage();
        storage.saveData(lockerList);
    }
}
