package duke.parser;

import duke.exceptions.DukeException;
import duke.logic.commands.Command;
import duke.logic.commands.EditLockerCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditLockerCommandParser {
    private List<String> splitInput;

    /**
     * This function is used to parse the user input for editing the status of a locker.
     * @param fullCommand stores the user input
     * @return reference to the class EditLockerCommand
     * @throws DukeException when the user input is invalid
     */

    public Command parse(String fullCommand) throws DukeException {
        if (fullCommand.trim().length() == 4) {
            throw new DukeException(" The description of edit command cant be empty");
        }

        splitInput = new ArrayList<String>(Arrays.asList(fullCommand.split(" ")));
        return new EditLockerCommand(splitInput);
    }
}
