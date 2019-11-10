package duke.parser;

import duke.exceptions.DukeException;
import duke.logic.commands.DeleteUsageCommand;
import duke.models.locker.SerialNumber;

import static java.util.Objects.requireNonNull;

/**
 * Parses the user input and creates a new DeleteUsageCommand object.
 */
public class DeleteUsageCommandParser {

    /**
     * Parses the user input for deleting the usage of an in-use locker from the list.
     * @param args stores the user input
     * @return reference to the class DeleteUsageCommand
     * @throws DukeException when the command format is invalid
     */
    public DeleteUsageCommand parse(String args) throws DukeException {
        requireNonNull(args);
        if (args.trim().length() == 0) {
            throw new DukeException(DeleteUsageCommand.INVALID_FORMAT);
        }
        SerialNumber serialNumber = ParserCheck.parseSerialNumber(args.trim());
        return new DeleteUsageCommand(serialNumber);

    }
}