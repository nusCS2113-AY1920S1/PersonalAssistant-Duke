package duke.parser;

import duke.exceptions.DukeException;
import duke.logic.commands.AddLockerCommand;
import duke.logic.commands.Command;

import java.util.ArrayList;
import java.util.List;

public class AddLockerCommandParser {

    private List<String> splitInput = new ArrayList<>();

    /**
     * This function is used to parse the user input for adding a new locker to the list.
     * Later it will include all checks for validating the user input
     * @param fullCommand stores the user input
     * @return reference to the class AddLockerCommand
     * @throws DukeException when the command syntax is invalid
     */

    public Command parse(String fullCommand) throws DukeException {
        if (fullCommand.trim().length() == 9) {
            throw new DukeException(" The description of addLocker command cant be empty");
        }
        int indexOfSerialPrefix = fullCommand.indexOf("s/");
        int indexOfAreaPrefix = fullCommand.indexOf("a/");
        int indexOfZonePrefix = fullCommand.indexOf("z/");

        if (indexOfSerialPrefix < indexOfAreaPrefix && indexOfAreaPrefix < indexOfZonePrefix
                && indexOfAreaPrefix != -1 && indexOfSerialPrefix != -1 && indexOfZonePrefix != -1) {
            splitInput.add(fullCommand.substring(indexOfSerialPrefix + 2, indexOfAreaPrefix).trim());
            splitInput.add(fullCommand.substring(indexOfAreaPrefix + 2, indexOfZonePrefix).trim());
            splitInput.add(fullCommand.substring(indexOfZonePrefix + 2));
        } else {
            throw new DukeException("Invalid command syntax");
        }
        return new AddLockerCommand(splitInput);
    }
}

