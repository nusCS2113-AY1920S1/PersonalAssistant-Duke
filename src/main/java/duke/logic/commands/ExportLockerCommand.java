package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.storage.Storage;
import duke.ui.Ui;

public class ExportLockerCommand extends Command {

    public static final String COMMAND_WORD = "export";

    @Override
    public void execute(LockerList lockerList, Ui ui, Storage storage) throws DukeException {
        ui.exportMessage();
        storage.exportAsCsv(lockerList);
    }
}
