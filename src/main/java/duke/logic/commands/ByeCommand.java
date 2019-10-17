package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.storage.FileHandling;
import duke.ui.Ui;

public class ByeCommand extends Command {

    @Override
    public void execute(LockerList lockerList, Ui ui, FileHandling storage) throws DukeException {
        this.isExit = true;
        ui.exitDuke();
    }
}
