package duke.parser;

import duke.exceptions.DukeException;
import duke.logic.commands.Command;
import duke.logic.commands.DeleteLockerCommand;

import java.util.ArrayList;
import java.util.List;

public class DeleteLockerCommandParser {
    private List<String> splitInput = new ArrayList<>();

    /**
     * This function is used to parse the user input for deleting a locker from the list.
     * @param fullCommand stores the user input
     * @return reference to the class DeleteLockerCommand
     * @throws DukeException when the command format is invalidd
     */

    public Command parse(String fullCommand) throws DukeException {
        if (fullCommand.trim().length() == 6) {
            throw new DukeException(" The description of delete command cant be empty");
        }

        String str = fullCommand.substring(7).trim();
        splitInput.add(str);
        return new DeleteLockerCommand(splitInput);
    }
}
