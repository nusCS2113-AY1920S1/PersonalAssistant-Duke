package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.storage.Storage;
import duke.storage.Stats;
import duke.ui.Ui;

public class StatsCommand extends Command {

    @Override
    public void execute(LockerList lockerList, Ui ui, Storage storage) throws DukeException {
        ui.readStats();
        Stats.readStats(lockerList.getLockerList());
    }
}
