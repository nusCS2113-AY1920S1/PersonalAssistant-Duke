package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.locker.Locker;
import duke.models.LockerList;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Command for adding a batch of lockers to SpongeBob.
 */
public class AddBatchCommand extends Command {

    private final List<Locker> batchOfLockers;

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

        if (lockerList.areLockersPresent(batchOfLockers)) {
            throw new DukeException(LockerList.DUPLICATE_LOCKERS_FOUND);
        }
        lockerList.addAllLockersInList(batchOfLockers);
        ui.printBatch(batchOfLockers.size());
        storage.saveData(lockerList);
    }
}
