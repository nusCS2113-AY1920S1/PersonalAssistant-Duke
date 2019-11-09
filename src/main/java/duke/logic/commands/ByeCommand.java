package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.storage.Storage;
import duke.ui.Ui;

public class ByeCommand extends Command {
    public static final String COMMAND_WORD = "bye";

    @Override
    public void execute(LockerList lockerList, Ui ui, Storage storage) {
        this.isExit = true;
        ui.exitDuke();
    }
}
