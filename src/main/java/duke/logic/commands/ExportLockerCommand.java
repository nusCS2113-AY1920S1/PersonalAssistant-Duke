package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.storage.OpenCsv;
import duke.storage.Storage;
import duke.ui.Ui;

public class ExportLockerCommand extends Command {

    @Override
    public void execute(LockerList lockerList, Ui ui, Storage storage) throws DukeException {
        ui.exportMessage();
        storage.exportAsCsv(lockerList);
    }
}
