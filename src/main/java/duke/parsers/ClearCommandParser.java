package duke.parsers;

import duke.commands.ClearCommand;
import duke.exceptions.DukeException;

public class ClearCommandParser implements ParserInterface<ClearCommand> {

    @Override
    public ClearCommand parse(String UserInput) throws DukeException {
        if (UserInput.trim().length() != 0) {
            String[] splitArgs = UserInput.split(" ", 2);
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
