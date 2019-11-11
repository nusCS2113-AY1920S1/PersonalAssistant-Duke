package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.storage.Storage;
import duke.ui.Ui;

import static java.util.Objects.requireNonNull;

/**
 * Command to redo the previous command.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    @Override
    public void execute(LockerList lockerList, Ui ui, Storage storage) throws DukeException {

        requireNonNull(lockerList);
        requireNonNull(ui);
        requireNonNull(storage);
        lockerList.updateLockerList(storage.redoStateList());
        storage.saveData(lockerList);
    }
}
