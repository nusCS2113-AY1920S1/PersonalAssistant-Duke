package duke.parser;

import duke.exceptions.DukeException;
import duke.logic.commands.AddBatchCommand;
import duke.logic.commands.Command;

import java.util.ArrayList;
import java.util.List;

public class AddBatchCommandParser {

    private List<String> splitInput = new ArrayList<>();

    /**
     * This function is used to parse the user input for adding batches.
     * Later it will be included with all the possible checks for the validity of the command
     * @param fullCommand stores the command entered by the user
     * @return reference to the the class AddBatchCommand
     * @throws DukeException when the command syntax is invalid
     */
    public Command parse(String fullCommand) throws DukeException {
        if (fullCommand.trim().length() == 8) {
            throw new DukeException(" The description of add batch command cant be empty");
        }
        int indexOfNumberPrefix = fullCommand.indexOf("n/");
        int indexOfSerialPrefix = fullCommand.indexOf("s/");
        int indexOfAreaPrefix = fullCommand.indexOf("a/");
        int indexOfZonePrefix = fullCommand.indexOf("z/");

        if (indexOfNumberPrefix < indexOfSerialPrefix && indexOfSerialPrefix < indexOfAreaPrefix
                && indexOfAreaPrefix < indexOfZonePrefix && indexOfAreaPrefix != -1
                && indexOfSerialPrefix != -1 && indexOfZonePrefix != -1 && indexOfNumberPrefix != -1) {
            splitInput.add(fullCommand.substring(indexOfNumberPrefix + 2,indexOfSerialPrefix).trim());
            splitInput.add(fullCommand.substring(indexOfSerialPrefix + 2, indexOfAreaPrefix).trim());
            splitInput.add(fullCommand.substring(indexOfAreaPrefix + 2, indexOfZonePrefix).trim());
            splitInput.add(fullCommand.substring(indexOfZonePrefix + 2));
        } else {
            throw new DukeException("Invalid command syntax");
        }
        return new AddBatchCommand(splitInput);
    }
}

