package duke.parser;

import duke.exceptions.DukeException;
import duke.logic.commands.RedoCommand;

public class RedoCommandParser {

    /**
     * This function is used to parse the user input for redoing previous commands that undo by user.
     * @return reference to the class RedoCommand
     * @throws DukeException when the command format is invalid
     **/

    public RedoCommand parse(String fullCommand) throws DukeException {

        if (fullCommand.trim().length() > 4) {
            throw new DukeException(" There should be no description for a redo command");
        }

        return new RedoCommand();
    }
}