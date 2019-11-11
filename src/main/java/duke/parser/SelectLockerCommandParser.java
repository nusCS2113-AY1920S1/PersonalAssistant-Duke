package duke.parser;

import duke.exceptions.DukeException;
import duke.logic.commands.SelectLockerCommand;
import duke.models.locker.SerialNumber;

import static java.util.Objects.requireNonNull;


public class SelectLockerCommandParser {

    /**
     * This function is used to parse the user input for selecting a locker.
     * @param args stores the user input
     * @return reference to the class SelectCommand
     * @throws DukeException when the command format is invalid
     */
    public SelectLockerCommand parse(String args) throws DukeException {
        requireNonNull(args);
        if (args.trim().length() == 0) {
            throw new DukeException(SelectLockerCommand.INVALID_FORMAT);
        }

        SerialNumber serialNumber = ParserCheck.parseSerialNumber(args.trim());
        return new SelectLockerCommand(serialNumber);
    }
}

