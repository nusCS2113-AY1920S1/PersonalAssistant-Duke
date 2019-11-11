package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.storage.Storage;
import duke.ui.Ui;

/**
 * Command to display a list of command history.
 */
public class HistoryCommand extends Command {

    public static final String COMMAND_WORD = "history";

    @Override
    public void execute(LockerList lockerList, Ui ui, Storage storage) throws DukeException {
        ui.printHistory(storage.getHistoryList());
    }
}
