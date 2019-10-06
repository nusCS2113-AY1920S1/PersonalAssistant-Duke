package duke.commands;

import duke.commons.DukeException;
import duke.data.Location;
import duke.data.tasks.Holiday;
import duke.storage.Storage;
import duke.ui.Ui;
import duke.parsers.api.ApiParser;

/**
 * Class representing a command to send the test URL connection.
 */
public class LocationSearchCommand extends Command {
    private String param;

    public LocationSearchCommand(String param) {
        this.param = param;
    }

    /**
     * Executes this command with given param.
     */
    @Override
    public void execute(Ui ui, Storage storage) throws DukeException {
        Location result = ApiParser.getLocationSearch(param);
        ui.show("These are the coordinates of your search:\n"
                + result.getAddress() + "\n" + result.getLatitude() + " "
                + result.getLongitude());
    }
}
