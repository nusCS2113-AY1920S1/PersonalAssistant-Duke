package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.Locker;
import duke.models.LockerList;
import duke.storage.FileHandling;
import duke.ui.Ui;


public class AddLockerCommand extends Command {

    private final Locker addLocker;

    public AddLockerCommand(Locker addLocker) {
        this.addLocker = addLocker;
    }

    @Override
    public void execute(LockerList lockerList, Ui ui, FileHandling storage) throws DukeException {

        lockerList.addLocker(addLocker);
        String lockerA = addLocker.toString();
        ui.printAddLocker(lockerList.getAllLockers(),lockerA);
        storage.saveData(lockerList);
    }
}
