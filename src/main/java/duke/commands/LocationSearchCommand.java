package duke.commands;

import duke.commons.exceptions.DukeException;
import duke.data.Location;
import duke.logic.api.ApiParser;
import duke.storage.Storage;

/**
 * Class representing a command to send the test URL connection.
 */
public class LocationSearchCommand extends Command {
    private String param;
    private Location result;

    public LocationSearchCommand(String param) throws DukeException {
        this.param = param;
        result = ApiParser.getLocationSearch(this.param);
    }

    /**
     * Executes this command with given param.
     */
    @Override
    public CommandResult execute(Storage storage) throws DukeException {
        return new CommandResult("These are the coordinates of your search:\n"
                + result.getAddress() + "\n" + result.getLatitude() + " "
                + result.getLongitude());
    }
}
