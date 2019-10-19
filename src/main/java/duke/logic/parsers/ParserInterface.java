package duke.logic.parsers;

import duke.logic.commands.Command;
import duke.commons.exceptions.DukeException;

public interface ParserInterface<T extends Command> {

    T parse(String userInput) throws DukeException;
}

