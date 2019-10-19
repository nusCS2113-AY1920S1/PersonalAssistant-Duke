package duke.logic.parsers;

import duke.logic.commands.ClearCommand;
import duke.commons.exceptions.DukeException;

public class ClearCommandParser implements ParserInterface<ClearCommand> {

    @Override
    public ClearCommand parse(String userInput) throws DukeException {
        if (userInput.trim().length() != 0) {
            String[] splitArgs = userInput.split(" ", 2);
            if (splitArgs.length >= 2) {
                return new ClearCommand(splitArgs[0], splitArgs[1]);
            } else {
                throw new DukeException("Please enter 2 dates; Start and End dates to clear meals from.");
            }
        } else {
            throw new DukeException("Please enter 2 dates; Start and End dates to clear meals from.");
        }
    }
}
