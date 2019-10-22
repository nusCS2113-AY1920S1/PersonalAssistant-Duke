package duke.logic.parsers;

import duke.commons.exceptions.DukeException;
import duke.logic.commands.SuggestCommand;

public class SuggestCommandParser implements ParserInterface<SuggestCommand> {
    @Override
    public SuggestCommand parse(String userInput) throws DukeException {
        // TODO: Finalize suggest command input format and update UG/DG
        return new SuggestCommand();
    }
}
