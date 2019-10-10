package duke.commands;

import duke.commons.exceptions.DukeException;
import duke.data.Location;
import duke.parsers.api.ApiParser;
import duke.storage.Storage;
import duke.ui.Ui;

/**
 * Class representing a command to send the test URL connection.
 */
public class LocationSearchCommand extends Command {
    private String param;
    private Location result;

    public LocationSearchCommand(String param) throws DukeException {
        this.param = param;
        result = ApiParser.getLocationSearch(param);
    }

    /**
     * Executes this command with given param.
     */
    @Override
    public void execute(Ui ui, Storage storage) throws DukeException {
        ui.show("These are the coordinates of your search:\n"
                + result.getAddress() + "\n" + result.getLatitude() + " "
                + result.getLongitude());
    }
}
