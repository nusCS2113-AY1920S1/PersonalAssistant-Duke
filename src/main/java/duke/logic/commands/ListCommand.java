package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.storage.FileHandling;
import duke.ui.Ui;

import static java.util.Objects.requireNonNull;

public class ListCommand extends Command {

    @Override
    public void execute(LockerList lockerList, Ui ui, FileHandling storage) throws DukeException {
        requireNonNull(lockerList);
        ui.printList(lockerList.getLockerList());
    }
}
