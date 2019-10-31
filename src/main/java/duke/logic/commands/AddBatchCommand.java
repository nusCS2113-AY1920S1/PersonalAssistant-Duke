package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.locker.Locker;
import duke.models.LockerList;
import duke.storage.FileHandling;
import duke.ui.Ui;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class AddBatchCommand extends Command {

    private final List<Locker> batchOfLockers;

    public AddBatchCommand(List<Locker> batchOfLockers) {
        requireNonNull(batchOfLockers);
        this.batchOfLockers = batchOfLockers;
    }

    @Override
    public void execute(LockerList lockerList, Ui ui, FileHandling storage) throws DukeException {

        if (lockerList.areLockersPresent(batchOfLockers)) {
            throw new DukeException("Duplicate entries not allowed. Serial number "
                    + " for every locker should be unique");
        }
        lockerList.addAllLockersInList(batchOfLockers);
        ui.printBatch(batchOfLockers.size());
        storage.saveData(lockerList);
    }
}
