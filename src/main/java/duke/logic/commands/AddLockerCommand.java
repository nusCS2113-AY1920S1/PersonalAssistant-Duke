package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.log.Log;
import duke.models.locker.Locker;
import duke.models.LockerList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

/**
 * Command for adding a locker to SpongeBob.
 */
public class AddLockerCommand extends Command {

    private static final String LOG_ADDING_LOCKER_COMMAND = " Executing command for adding a locker";
    private final Locker addLocker;
    public static final String COMMAND_WORD = "addlocker";
    public static final String INVALID_FORMAT = " Invalid command format for adding a locker."
            + "\n     1. All tokens should be present (s/ z/ a/) "
            + "\n     2. There should not include any text between the command word and the first token.";
    private static final Logger logger = Log.getLogger();

    /**
     * Creates an AddLockerCommand to add {@code Locker}.
     */
    public AddLockerCommand(Locker addLocker) {
        requireNonNull(addLocker);
        this.addLocker = addLocker;
    }

    @Override
    public void execute(LockerList lockerList, Ui ui, Storage storage) throws DukeException {
        logger.log(Level.INFO, LOG_ADDING_LOCKER_COMMAND);
        if (lockerList.isPresentLocker(addLocker)) {
            throw new DukeException(LockerList.DUPLICATE_LOCKERS_FOUND);
        }
        lockerList.addLocker(addLocker);
        ui.printAddLocker(lockerList.numLockers(), addLocker.toString());
        storage.saveData(lockerList);
        storage.updateStateList(lockerList);
    }
}
