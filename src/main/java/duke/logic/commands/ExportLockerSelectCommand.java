package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.storage.Storage;
import duke.ui.Ui;

import static java.util.Objects.requireNonNull;

public class ExportLockerSelectCommand extends Command {
    private final String argument;
    public static final String INVALID_FORMAT = " Invalid command format for exporting selection of csv file. "
            + "You must key in 'exports' with tags such as 'Locker,Name,Zone' etc.";

    public static final String COMMAND_WORD = "exports";

    public static final String MISSINGLOCKER_FORMAT = "Unable to export CSV file without 'Locker' input. "
            + "You must key in tags with spaces in between. "
            + "Example: 'exports locker zone status name'";

    public static final String MISSINGSTATUS_FORMAT = "Unable to pull student info without 'Status' input";


    public ExportLockerSelectCommand(String arg) {
        requireNonNull(arg);
        this.argument = arg;
    }

    @Override
    public void execute(LockerList lockerList, Ui ui, Storage storage) throws DukeException {
        ui.exportSelect();
        storage.exportSelection(lockerList,argument);
    }
}
