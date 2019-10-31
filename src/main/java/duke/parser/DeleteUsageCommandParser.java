package duke.parser;

import duke.exceptions.DukeException;
import duke.logic.commands.Command;
import duke.logic.commands.DeleteUsageCommand;
import duke.models.locker.SerialNumber;


public class DeleteUsageCommandParser {

    /**
     * This function is used to parse the user input for deleting a locker from the list.
     * @param args stores the user input
     * @return reference to the class DeleteLockerCommand
     * @throws DukeException when the command format is invalid
     */

    public Command parse(String args) throws DukeException {

        if (args.trim().length() == 0) {
            throw new DukeException(" Invalid command format. You must key in the serial number");
        }
        SerialNumber serialNumber = ParserCheck.parseSerialNumber(args.trim());
        return new DeleteUsageCommand(serialNumber);

    }
}