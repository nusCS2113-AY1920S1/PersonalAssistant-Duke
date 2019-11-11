package duke.parser;

import duke.exceptions.DukeException;
import duke.logic.commands.UndoCommand;

public class UndoCommandParser {

    /**
     * This function is used to parse the user input for undoing previous commands typed in by user.
     * @return reference to the class UndoCommand
     * @throws DukeException when the command format is invalid
     **/

    public UndoCommand parse(String fullCommand) throws DukeException {

        if (fullCommand.trim().length() > 4) {
            throw new DukeException(" There should be no description for an undo command");
        }

        return new UndoCommand();
    }
}

