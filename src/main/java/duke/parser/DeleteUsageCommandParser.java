package duke.parser;

import duke.exceptions.DukeException;
import duke.logic.commands.DeleteUsageCommand;
import duke.models.locker.SerialNumber;

import static java.util.Objects.requireNonNull;


public class DeleteUsageCommandParser {

    /**
     * This function is used to parse the user input for deleting a locker from the list.
     * @param args stores the user input
     * @return reference to the class DeleteLockerCommand
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