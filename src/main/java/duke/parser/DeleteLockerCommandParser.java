package duke.parser;

import duke.exceptions.DukeException;
import duke.log.Log;
import duke.logic.commands.DeleteLockerCommand;
import duke.models.locker.SerialNumber;

import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

/**
 * Parses the user input and creates a new DeleteLocker object.
 */
public class DeleteLockerCommandParser {
    private static final Logger logger = Log.getLogger();
    private static final String LOG_DELETE_LOCKER_COMMAND = "Attempting to parse user input for DeleteLockerCommand";

    /**
     * Parses the user input for deleting a locker from the list.
     * @param args stores the user input
     * @return reference to the class DeleteLockerCommand
     * @throws DukeException when the command format is invalid
     */
    public DeleteLockerCommand parse(String args) throws DukeException {
        logger.log(Level.INFO, LOG_DELETE_LOCKER_COMMAND);
        requireNonNull(args);
        if (args.trim().length() == 0) {
            throw new DukeException(DeleteLockerCommand.INVALID_FORMAT);
        }

        SerialNumber serialNumber = ParserCheck.parseSerialNumber(args.trim());
        return new DeleteLockerCommand(serialNumber);
    }
}
