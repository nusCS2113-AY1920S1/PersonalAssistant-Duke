package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.log.Log;
import duke.models.locker.Locker;
import duke.models.LockerList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

/**
 * Command for adding a batch of lockers to SpongeBob.
 */
public class AddBatchCommand extends Command {

    private static final String LOG_FOR_ADDING_BATCH_COMMAND = " Executing command for adding batch of lockers";
    private final List<Locker> batchOfLockers;
    private static final Logger logger = Log.getLogger();

    public static final String COMMAND_WORD = "addbatch";
    public static final String INVALID_FORMAT = " Invalid command format for adding batch of lockers."
            + "\n     1. All tokens should be present. (s/ u/ z/ a/) "
            + "\n     2. There should not include any text between the command word and the first token.";

    /**
     * Creates an AddBatchCommand to add {@code List<Locker>}.
     */
    public AddBatchCommand(List<Locker> batchOfLockers) {
        requireNonNull(batchOfLockers);
        this.batchOfLockers = batchOfLockers;
    }


    @Override
    public void execute(LockerList lockerList, Ui ui, Storage storage) throws DukeException {
        logger.log(Level.INFO, LOG_FOR_ADDING_BATCH_COMMAND);
        if (lockerList.areLockersPresent(batchOfLockers)) {
            throw new DukeException(LockerList.DUPLICATE_LOCKERS_FOUND);
        }
        lockerList.addAllLockersInList(batchOfLockers);
        ui.printBatch(batchOfLockers.size());
        storage.saveData(lockerList);
        storage.updateStateList(lockerList);
    }
}
